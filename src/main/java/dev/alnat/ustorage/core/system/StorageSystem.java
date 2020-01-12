package dev.alnat.ustorage.core.system;

import dev.alnat.ustorage.core.model.SystemConfiguration;
import dev.alnat.ustorage.exception.UStorageException;

import java.util.List;
import java.util.Map;

/**
 * Интерфейс для систем хранения
 *
 * Created by @author AlNat on 08.01.2020.
 * Licensed by Apache License, Version 2.0
 */
public interface StorageSystem {

    /**
     * Метод сохранения файла
     *
     * @param file данные файла
     * @param filename имя файла
     * @throws UStorageException при ошибке
     */
    void saveFile(byte[] file, String filename) throws UStorageException;

    /**
     * Метод получения файла по имени
     *
     * @param filename имя файла
     * @throws UStorageException при ошибке
     */
    byte[] fetchFile(String filename) throws UStorageException;

    /**
     * Метод удаления файла из хранилища
     *
     * @param filename имя файла
     * @throws UStorageException при ошибке
     */
    void deleteFile(String filename) throws UStorageException;

    /**
     * Метод получения списка имен файлов, находящихся в хранилище
     *
     * @return список файлов
     * @throws UStorageException при ошибке
     */
    List<String> getFileNameList() throws UStorageException;

    /**
     * Метод получения списка файлов из хранилища
     * Ключ - имя файл в хранилище
     * Значение - сам файл
     *
     * @return список файлов в хранилище
     * @throws UStorageException при ошибке
     */
    Map<String, byte[]> getAllFile() throws UStorageException;

    /**
     * Получения конфигурации этой системы из БД для связи
     *
     * @return модель для связи
     */
    SystemConfiguration getSystemConfiguration();

}
