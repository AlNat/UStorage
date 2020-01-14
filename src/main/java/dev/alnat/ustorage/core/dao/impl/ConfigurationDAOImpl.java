package dev.alnat.ustorage.core.dao.impl;

import dev.alnat.ustorage.core.dao.ConfigurationDAO;
import dev.alnat.ustorage.core.model.Configuration;
import dev.alnat.ustorage.core.model.StorageTypeEnum;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;


/**
 * Created by @author AlNat on 08.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@Repository
public class ConfigurationDAOImpl implements ConfigurationDAO {

    // TODO Перейти на нормальный EntityManager

    private SessionFactory sessionFactory;

    // HibernateJpaSessionFactoryBean

    @Autowired
    public ConfigurationDAOImpl(EntityManagerFactory factory) {
        if(factory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.sessionFactory = factory.unwrap(SessionFactory.class);
    }

    @Override
    public String get(String name) {
        return getByName(name).getValue();
    }

    @Override
    public Configuration getByName(String name) {
        return this.sessionFactory.getCurrentSession()
                .createQuery("FROM Configuration c WHERE c.name = :name", Configuration.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public String getStorageSystemKeyByType(StorageTypeEnum storageType) {
        Configuration c = getByName(storageType.name() + "_DEFAULT_STORAGE_SYSTEM");
        return c.getValue();
    }


    /**
     * Класс-хранилище для параметров конфигурации
     * Сами параметры зашиты
     *
     * Можно было бы сделать через Enum, но смысла мало
     */
    static class ConfigurationParam {

        public static String VERSION = "VERSION";

    }

}
