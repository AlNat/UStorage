package dev.alnat.ustorage.core.dao.impl;

import dev.alnat.ustorage.core.dao.ConfigurationDAO;
import dev.alnat.ustorage.core.model.Configuration;
import dev.alnat.ustorage.core.model.StorageTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;


/**
 * Created by @author AlNat on 08.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@Repository
public class ConfigurationDAOImpl implements ConfigurationDAO {

    private final EntityManager entityManager;

    @Autowired
    public ConfigurationDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public String get(String name) {
        return getByName(name).getValue();
    }

    @Override
    public Configuration getByName(String name) {
        return this.entityManager
                .createQuery("FROM Configuration c WHERE c.name = :name", Configuration.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public String getStorageSystemKeyByType(StorageTypeEnum storageType) {
        Configuration c = getByName(storageType.name() + "_DEFAULT_STORAGE_SYSTEM");
        return c.getValue();
    }

}
