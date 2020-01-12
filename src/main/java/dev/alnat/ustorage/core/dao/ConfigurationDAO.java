package dev.alnat.ustorage.core.dao;

import dev.alnat.ustorage.core.model.Configuration;
import dev.alnat.ustorage.core.model.StorageTypeEnum;

/**
 * Created by @author AlNat on 08.01.2020.
 * Licensed by Apache License, Version 2.0
 */
public interface ConfigurationDAO {

    Configuration getByName(String name);
    String getStorageSystemKeyByType(StorageTypeEnum storageType);

}