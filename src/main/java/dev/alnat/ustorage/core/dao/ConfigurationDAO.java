package dev.alnat.ustorage.core.dao;

import dev.alnat.ustorage.core.model.Configuration;
import dev.alnat.ustorage.core.model.StorageTypeEnum;

/**
 * Created by @author AlNat on 08.01.2020.
 * Licensed by Apache License, Version 2.0
 */
public interface ConfigurationDAO {

    /**
     * Получение полной записи по ключу
     *
     * @param name ключ конфигурации
     * @return полную запись
     */
    Configuration getByName(String name);

    /**
     * Получения значения по ключу
     * @param name ключ конфигурации
     * @return текстовое значение из конфигурации
     */
    String get(String name);

    /**
     * Получение ключа системы, дефолтной для данного типа хранения
     *
     * @param storageType тип сохранения
     * @return ключ системы, если отсутсвует - null
     */
    String getStorageSystemKeyByType(StorageTypeEnum storageType);

}
