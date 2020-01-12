package dev.alnat.ustorage.core.system;

import dev.alnat.ustorage.core.model.SystemConfiguration;
import dev.alnat.ustorage.exception.UStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by @author AlNat on 08.01.2020.
 * Licensed by Apache License, Version 2.0
 */
public abstract class AbstractStorageSystem implements StorageSystem {

    protected Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Данные о конфигурации из БД
     */
    protected SystemConfiguration systemConfiguration;

    public AbstractStorageSystem(SystemConfiguration systemConfiguration) {
        this.systemConfiguration = systemConfiguration;
    }

    /**
     * Метод пост-инициализации системы
     *
     * @throws UStorageException при ошибке
     */
    protected void init() throws UStorageException {
        log.debug("Система {} была успешно проинициализирована", getClass().getName());
    }

    /**
     * Метод валидации конфигурации системы
     *
     * @return true если конфигурация валидна и false если нет
     */
    protected boolean validateConfig() {
        return !systemConfiguration.getConfiguration().isEmpty();
    }

    @Override
    public SystemConfiguration getSystemConfiguration() {
        return systemConfiguration;
    }

}
