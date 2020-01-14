package dev.alnat.ustorage.core.service;

import dev.alnat.ustorage.core.dao.ConfigurationDAO;
import dev.alnat.ustorage.core.model.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO Описание
 *
 * Created by @author AlNat on 12.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@Service
@Transactional
public class ConfigurationService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final ConfigurationDAO configurationDao;


    public ConfigurationService(ConfigurationDAO configurationDao) {
        this.configurationDao = configurationDao;
    }


}
