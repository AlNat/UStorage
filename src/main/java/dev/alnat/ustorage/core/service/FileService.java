package dev.alnat.ustorage.core.service;

import dev.alnat.ustorage.core.dao.FileDAO;
import dev.alnat.ustorage.core.dao.UserDAO;
import dev.alnat.ustorage.core.model.File;
import dev.alnat.ustorage.core.model.FileDTO;
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


    /**
     * Метод сохранения файла в дефолтную для данного типа систему
     *
     * @param file файл для загрузки
     * @param storageType тип системы для сохранения
     * @return имя файла в системе
     * @throws UStorageException при ошибке
     */
    public String saveFile(MultipartFile file, StorageTypeEnum storageType) throws UStorageException {
        StorageSystem storageSystem = systemFactory.getExternalSystem(storageType);

        // TODO ЗАШИТО ДЛЯ ТЕСТОВ! Удалить после добавления Security
        User u = userDAO.getUserByLogin("test");

        return saveFileToSystem(file, storageSystem, u);
    }

    /**
     *
     *
     * @param file
     * @param systemKey
     * @return
     * @throws UStorageException
     */
    public String saveFile(MultipartFile file, String systemKey) throws UStorageException {
        StorageSystem storageSystem = systemFactory.getExternalSystem(systemKey);

        // TODO ЗАШИТО ДЛЯ ТЕСТОВ! Удалить после добавления Security
        User u = userDAO.getUserByLogin("test");

        return saveFileToSystem(file, storageSystem, u);
    }

    /**
     *
     *
     * @param file
     * @param storageSystem
     * @param u
     * @return
     * @throws UStorageException
     */
    private String saveFileToSystem(MultipartFile file, StorageSystem storageSystem, User u) throws UStorageException {
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

        return storageFileName;
    }

    /**
     *
     *
     * @param fileName
     * @return
     * @throws UStorageException
     */
    public FileDTO download(String fileName) throws UStorageException {
        File savedFile = fileDAO.getByFilename(fileName);
        String storageSystemKey;

        // Выбираем из какой системы будем читать
        if (savedFile.getStorageSystemCount() < 1) {
            log.error("");
            throw new UStorageException("");
        } else if (savedFile.getStorageSystemCount() > 1) {
            // TODO Выбирать системы с которой нужно читать в зависимости от политики...
            storageSystemKey = savedFile.getStorageSystemKeyList().get(0);
        } else {
            storageSystemKey = savedFile.getStorageSystemKeyList().get(0);
        }

        // Получаем систему хранения и вытскиваем из нее файл
        StorageSystem storageSystem = systemFactory.getExternalSystem(storageSystemKey);
        byte[] fileData = storageSystem.fetchFile(savedFile.getStorageFileName());

        return new FileDTO(savedFile, fileData);
    }

    // Удаление файла
    // Удаление файла в системе по имени
    // Удаление файла в системе по типу

    // Получения списка файлов
    // Получения списка файлов в системе по имени
    // Получения списка файлов в системе по типу

    // Получения списка имен файлов
    // Получения списка имен файлов в системе по имени
    // Получения списка имен файлов в системе по типу

}
