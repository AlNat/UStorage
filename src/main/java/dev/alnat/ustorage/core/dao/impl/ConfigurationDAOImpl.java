package dev.alnat.ustorage.core.dao.impl;

import dev.alnat.ustorage.core.dao.ConfigurationDAO;
import dev.alnat.ustorage.core.model.Configuration;
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

    private SessionFactory sessionFactory;

    @Autowired
    public ConfigurationDAOImpl(EntityManagerFactory factory) {
        if(factory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("Factory is not a hibernate factory");
        }

        this.sessionFactory = factory.unwrap(SessionFactory.class);
    }

    // TODO Список предустановленных конфигураций

    @Override
    public Configuration getByName(String name) {
        return this.sessionFactory.getCurrentSession()
                .createQuery("FROM Configuration c where c.name = :name", Configuration.class)
                .setParameter("name", name)
                .uniqueResult();
    }

}
