package dev.alnat.ustorage.core.system;

import dev.alnat.ustorage.core.dao.ConfigurationDAO;
import dev.alnat.ustorage.core.dao.SystemConfigurationDAO;
import dev.alnat.ustorage.core.model.StorageTypeEnum;
import dev.alnat.ustorage.core.model.SystemConfiguration;
import dev.alnat.ustorage.exception.UStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Фабрика для систем хранения
 *
 * Created by @author AlNat on 08.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@Service
public class SystemFactory {

    protected Logger log = LoggerFactory.getLogger(getClass());

    protected final AutowireCapableBeanFactory beanFactory;
    protected final ConfigurationDAO conf;
    protected final SystemConfigurationDAO systemConfigurationDAO;

    /**
     * Хранилище кэшированных систем 
     * Ключ - имя системы (key)
     * Значение - класс-обертка с метаинформацией и самой системой
     */
    protected volatile ConcurrentMap<String, SystemStorageWrapper> cachedSystems;

    public SystemFactory(AutowireCapableBeanFactory beanFactory, 
                         ConfigurationDAO conf, 
                         SystemConfigurationDAO systemConfigurationDAO) {
        this.beanFactory = beanFactory;
        this.conf = conf;
        this.systemConfigurationDAO = systemConfigurationDAO;

        this.cachedSystems = new ConcurrentHashMap<>();
    }

    /**
     * Функция получения системы по типу.
     * Дефолтная система для данного типа берется из конфигурации
     *
     * @param storageType тип системы хранения
     * @return система хранения
     * @throws UStorageException при ошибке
     */
    public synchronized AbstractStorageSystem getExternalSystem(StorageTypeEnum storageType) throws UStorageException {
        String defaultSystemKey = conf.getStorageSystemKeyByType(storageType);

        if (defaultSystemKey == null || defaultSystemKey.isEmpty()) {
            throw new UStorageException("Для типа хранения = " + storageType.getText() + " не найдено системы по умолчанию!");
        }

        return getExternalSystem(defaultSystemKey);
    }

    /**
     * Функция получения системы по имени
     *
     * @param systemName имя системы
     * @return система хранения
     * @throws UStorageException при ошибке
     */
    public synchronized AbstractStorageSystem getExternalSystem(String systemName) throws UStorageException {
        systemName = systemName.toUpperCase();

        // Если система уже кеширована - то берем ее
        if (cachedSystems != null && cachedSystems.containsKey(systemName)) {
            SystemStorageWrapper wrapper = cachedSystems.get(systemName);

            // Если срок действия системы истек - удалем ее
            if (wrapper.getExpiredAt() != null && wrapper.getExpiredAt().isAfter(LocalDateTime.now())) {
                cachedSystems.remove(systemName);
            } else { // Иначе возвращаем ее, увеличивая счетчик обращений
                wrapper.getCount().incrementAndGet();
                return wrapper.getStorageSystem();
            }
        }

        // Если нет такой системы системы, то получаем ее данные конфигурации из БД и инициализируем, сохраняем в кеш
        SystemConfiguration systemConfiguration = systemConfigurationDAO.getByKey(systemName);
        initSystem(systemConfiguration);

        // После чего уже возвращаем ее
        SystemStorageWrapper wrapper = cachedSystems.get(systemName);
        wrapper.getCount().incrementAndGet();
        return wrapper.getStorageSystem();
    }

    /**
     * Функция инициализации и кэширования системы
     *
     * @param systemModel модель системы из БД
     */
    protected void initSystem(SystemConfiguration systemModel) throws UStorageException {
        AbstractStorageSystem system; // Кешированная система

        // Данные из БД
        String systemName = systemModel.getKey();
        String classHandler = systemModel.getClassHandler();
        String configuration = systemModel.getConfiguration();

        // Загружаем класс-плагин для этой системы
        Class<?> plugin;
        try {
            plugin = this.getClass().getClassLoader().loadClass(classHandler);
            log.debug("Класс {} успешно загружен", systemName);
        } catch (ClassNotFoundException e) {
            throw new UStorageException("Невозможно загрузить внешнюю систему(" + systemName + "): " + e.getMessage(), e);
        }

        // Загрузили конструктор с одним аргументом и передали туда систему из БД
        try {
            system = (AbstractStorageSystem) plugin
                    .getConstructor(systemModel.getClass())
                    .newInstance(systemModel);
            log.debug("Экземпляр класса {} успешно создан", systemName);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new UStorageException("Невозможно получить конструктор внешней системы(" + systemName + "): " + e.getMessage(), e);
        }

        // Внедряем туда все бины через Autowire
        beanFactory.autowireBean(system);

        // Проверяем валидность конфигурации
        if (!system.validateConfig()) {
            log.error("Ошибка при разборе конфигурации системы {}! Конфигурация {} - не валидна!", systemName, systemModel.getConfiguration());
            throw new UStorageException("У системы " + systemName + " невалидная конфигурация! Проверьте настройки");
        }

        system.init(); // Вызвали функцию пост-иницализации
        cachedSystems.put(systemName, new SystemStorageWrapper(system)); // Кэшировали систему в кеш
        log.info("Внешняя система({}) {} успешно создана", systemModel.getClass().getSimpleName(), systemName);
    }

    /**
     * Функция очистки кэшированной систем
     */
    public void clearCachedSystems() {
        cachedSystems = null;
    }
    
}


/**
 * Небольшой класс-обертка для хранения не чистой внешней системы (плагина), а обертки с дополнительной метаинформацией
 */
class SystemStorageWrapper {

    /**
     * Сама система
     */
    private AbstractStorageSystem storageSystem;

    /**
     * Дата и время создания
     */
    private LocalDateTime creationDate;

    /**
     * Число получений этой системы
     */
    private AtomicInteger count;

    /**
     * Возможное время истечения кеша
     */
    private LocalDateTime expiredAt;


    public SystemStorageWrapper() {
    }

    public SystemStorageWrapper(AbstractStorageSystem storageSystem) {
        this.storageSystem = storageSystem;
        this.creationDate = LocalDateTime.now();
        this.count = new AtomicInteger(0);
        this.expiredAt = null;
    }

    public SystemStorageWrapper(AbstractStorageSystem storageSystem, LocalDateTime expiredAt) {
        this.storageSystem = storageSystem;
        this.expiredAt = expiredAt;
        this.creationDate = LocalDateTime.now();
        this.count = new AtomicInteger(0);
    }


    public AbstractStorageSystem getStorageSystem() {
        return storageSystem;
    }

    public void setStorageSystem(AbstractStorageSystem storageSystem) {
        this.storageSystem = storageSystem;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public AtomicInteger getCount() {
        return count;
    }

    public void setCount(AtomicInteger count) {
        this.count = count;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemStorageWrapper that = (SystemStorageWrapper) o;
        return Objects.equals(storageSystem, that.storageSystem) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(count, that.count) &&
                Objects.equals(expiredAt, that.expiredAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storageSystem, creationDate, count, expiredAt);
    }

}

