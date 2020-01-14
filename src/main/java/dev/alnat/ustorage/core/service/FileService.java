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
import dev.alnat.ustorage.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
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
@Transactional
public class FileService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final FileDAO fileDAO;
    private final UserDAO userDAO;

    private final SystemFactory systemFactory;

    @Autowired
    public FileService(FileDAO fileDAO,
                       UserDAO userDAO,
                       SystemFactory systemFactory) {
        this.fileDAO = fileDAO;
        this.userDAO = userDAO;
        this.systemFactory = systemFactory;
    }

    public void saveFile(MultipartFile file, StorageTypeEnum storageType) throws UStorageException {
        StorageSystem storageSystem = systemFactory.getExternalSystem(storageType);

        // TODO ЗАШИТО ДЛЯ ТЕСТОВ! Удалить после добавления Security
        User u = userDAO.getUserByLogin("test");

        // Генерируем UUID файла
        String storageFileName = UUID.randomUUID().toString();
        byte[] fileData;

        try {
            fileData = file.getBytes();
        } catch (IOException e) {
            log.error("Ошибка при получении файла для сохранения - {}", e.getMessage());
            throw new UStorageException("Ошибки при получении файла для сохранения", e);
        }

        // Формируем сущность
        File savedFile = new File();
        savedFile.setCreationDate(LocalDateTime.now());
        savedFile.setStorageSystem(storageSystem.getSystemConfiguration());
        savedFile.setStorageFileName(storageFileName);
        savedFile.setUser(u);
        savedFile.setExtension(Util.getExtensionFromFilename(file.getOriginalFilename()));
        savedFile.setFilename(file.getOriginalFilename());

        // После чего сохраняем сам файл через систему, а затем саму сущность
        storageSystem.saveFile(fileData, storageFileName);
        fileDAO.save(savedFile);
    }

}
