package dev.alnat.ustorage.core.service;

import dev.alnat.ustorage.core.dao.SystemConfigurationDAO;
import dev.alnat.ustorage.core.system.SystemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Сервис для манипуляции с системами хранения
 *
 * Created by @author AlNat on 08.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@Service
@Transactional
public class SystemService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final SystemConfigurationDAO systemConfigurationDAO;
    private final SystemFactory systemFactory;

    @Autowired
    public SystemService(SystemConfigurationDAO systemConfigurationDAO,
                         SystemFactory systemFactory) {
        this.systemConfigurationDAO = systemConfigurationDAO;
        this.systemFactory = systemFactory;
    }



}
