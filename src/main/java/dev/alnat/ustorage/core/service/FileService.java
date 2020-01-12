package dev.alnat.ustorage.core.service;

import dev.alnat.ustorage.core.dao.FileDAO;
import dev.alnat.ustorage.core.dao.SystemConfigurationDAO;
import dev.alnat.ustorage.core.dao.UserDAO;
import dev.alnat.ustorage.core.model.File;
import dev.alnat.ustorage.core.model.SystemConfiguration;
import dev.alnat.ustorage.core.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

/**
 * Сервис для работы с файлами
 *
 * Created by @author AlNat on 08.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@Service
public class FileService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    FileDAO fileDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    SystemConfigurationDAO systemConfigurationDAO;


}
