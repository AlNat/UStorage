package dev.alnat.ustorage.core.service;

import dev.alnat.ustorage.config.RoleHolder;
import dev.alnat.ustorage.core.dao.FileDAO;
import dev.alnat.ustorage.core.dao.UserDAO;
import dev.alnat.ustorage.core.model.*;
import dev.alnat.ustorage.core.system.StorageSystem;
import dev.alnat.ustorage.core.system.SystemFactory;
import dev.alnat.ustorage.exception.UStorageException;
import dev.alnat.ustorage.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
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
     * Сохранение файла в конкретной системе
     *
     * @param file файл для сохранения
     * @param systemKey ключ системы
     * @return идентификатор файла в системе
     * @throws UStorageException при ошибке
     */
    public String saveFile(MultipartFile file, String systemKey) throws UStorageException {
        StorageSystem storageSystem = systemFactory.getExternalSystem(systemKey);

        // TODO ЗАШИТО ДЛЯ ТЕСТОВ! Удалить после добавления Security
        User u = userDAO.getUserByLogin("test");

        return saveFileToSystem(file, storageSystem, u);
    }

    /**
     * Сохранение файла в системе
     * Приватный метод для внутреннего использования - вынесение общей части из методов сохранения
     *
     * @param file файл для сохранения
     * @param storageSystem сама система
     * @param u пользователь, который инициировал сохранение
     * @return идентификатор файла в системе
     * @throws UStorageException при ошибке
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
     * Метод получения файла
     *
     * @param fileName оригинальное имя файл
     * @return обертка над файлом (сам файл, его имя и дата сохранения)
     * @throws UStorageException при ошибке
     */
    public FileDTO downloadByOriginalFileName(String fileName) throws UStorageException {
        File savedFile = fileDAO.getByFilename(fileName);
        String storageSystemKey;

        // Выбираем из какой системы будем читать
        if (savedFile.getStorageSystemCount() < 1) {
            log.error("");
            throw new UStorageException("");
        } else if (savedFile.getStorageSystemCount() > 1) {
            // TODO Выбирать системы с которой нужно читать в зависимости от политики (горячее\холодное хранилище и тд)
            storageSystemKey = savedFile.getStorageSystemKeyList().get(0);
        } else {
            storageSystemKey = savedFile.getStorageSystemKeyList().get(0);
        }

        // Получаем систему хранения и вытскиваем из нее файл
        StorageSystem storageSystem = systemFactory.getExternalSystem(storageSystemKey);
        byte[] fileData = storageSystem.fetchFile(savedFile.getStorageFileName());

        return new FileDTO(savedFile, fileData);
    }

    /**
     * Удаление файла по оригинальному имени
     *
     * @param fileName имя файла
     * @throws UStorageException при ошибке
     */
    public void deleteFile(String fileName) throws UStorageException {
        File fileToDelete = fileDAO.getByFilename(fileName);
        List<String> systemKeyList = fileToDelete.getStorageSystemKeyList();
        for (String key : systemKeyList) {
            systemFactory.getExternalSystem(key).deleteFile(fileToDelete.getStorageFileName());
        }
        fileDAO.delete(fileToDelete);
    }

    /**
     * Удаление файла по оригинальному имени в системе
     * Нужно если хочется удалить файл, сохраненный в нескольких системах, только в одном месте
     *
     * @param fileName оригинальное имя файла
     * @param systemKey ключ системы
     * @throws UStorageException при ошибке
     */
    public void deleteFile(String fileName, String systemKey) throws UStorageException {
        File fileToDelete = fileDAO.getByFilename(fileName);
        List<String> systemKeyList = fileToDelete.getStorageSystemKeyList();

        if (!systemKeyList.contains(systemKey)) {
            log.error(""); // TODO
            throw new UStorageException("");
        }

        StorageSystem system = systemFactory.getExternalSystem(systemKey);
        system.deleteFile(fileToDelete.getStorageFileName());

        // Если эта система была единственная для сохранения - удаляем сам файл
        if (systemKeyList.size() < 2) {
            fileDAO.delete(fileToDelete);
        } else {
            fileToDelete.getStorageSystemList().remove(system);
            fileDAO.save(fileToDelete);
        }
    }

    @PostConstruct
    public void test () {
        RoleHolder.generateAuthoritiesToUser(null);
    }

    // TODO:
    //  Получения списка файлов
    //  Получения списка файлов в системе по имени
    //  Получения списка файлов в системе по типу
    //  Получения списка имен файлов
    //  Получения списка имен файлов в системе по имени
    //  Получения списка имен файлов в системе по типу

}
