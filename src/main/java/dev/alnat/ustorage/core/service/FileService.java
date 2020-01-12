package dev.alnat.ustorage.core.service;

import dev.alnat.ustorage.core.dao.FileDAO;
import dev.alnat.ustorage.core.dao.SystemConfigurationDAO;
import dev.alnat.ustorage.core.dao.UserDAO;
import dev.alnat.ustorage.core.model.File;
import dev.alnat.ustorage.core.model.StorageTypeEnum;
import dev.alnat.ustorage.core.model.User;
import dev.alnat.ustorage.core.system.StorageSystem;
import dev.alnat.ustorage.core.system.SystemFactory;
import dev.alnat.ustorage.exception.UStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сервис для работы с файлами
 *
 * Created by @author AlNat on 08.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@Service
public class FileService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final FileDAO fileDAO;
    private final UserDAO userDAO;
    private final SystemConfigurationDAO systemConfigurationDAO;

    private final SystemFactory systemFactory;

    @Autowired
    public FileService(FileDAO fileDAO,
                       UserDAO userDAO,
                       SystemConfigurationDAO systemConfigurationDAO,
                       SystemFactory systemFactory) {
        this.fileDAO = fileDAO;
        this.userDAO = userDAO;
        this.systemConfigurationDAO = systemConfigurationDAO;
        this.systemFactory = systemFactory;
    }

    public void saveFile(MultipartFile file, StorageTypeEnum storageType) throws UStorageException {
        StorageSystem storageSystem = systemFactory.getExternalSystem(storageType);

        // TODO ЗАШИТО ДЛЯ ТЕСТОВ! Удалить после Security
        User u = userDAO.getUserByLogin("test");

        // Генерируем UUID файла
        String storageFileName = UUID.randomUUID().toString();
        byte[] fileData;

        try {
            fileData = file.getBytes();
        } catch (IOException e) {
            log.error(""); // TODO Добавить
            throw new UStorageException("");
        }

        File savedFile = new File();
        savedFile.setCreationDate(LocalDateTime.now());
        savedFile.setStorageSystem(storageSystem.getSystemConfiguration());
        savedFile.setStorageFileName(storageFileName);
        savedFile.setUser(u);
        savedFile.setFilename(file.getOriginalFilename());

        // После чего сохраняем его
        storageSystem.saveFile(fileData, storageFileName);
        fileDAO.save(savedFile);
    }

}
