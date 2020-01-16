package dev.alnat.ustorage.core.service;

import dev.alnat.ustorage.core.conf.Configuration;
import dev.alnat.ustorage.core.dao.ConfigurationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by @author AlNat on 16.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@Service
@Transactional
public class CommonService {

    private final ConfigurationDAO configurationDAO;


    @Autowired
    public CommonService(ConfigurationDAO configurationDAO) {
        this.configurationDAO = configurationDAO;
    }


    public String getVersion() {
        return configurationDAO.get(Configuration.VERSION);
    }

}
