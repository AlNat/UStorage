package dev.alnat.ustorage.core.system.filesystem;

import dev.alnat.ustorage.core.model.SystemConfiguration;
import dev.alnat.ustorage.exception.UStorageException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Примитивное сохранение в файловую систему как есть
 * Используется стандартное Java File API
 * @see java.nio.file
 *
 * Конфигурация представлет собой одну строку, где храниться полный путь к папке для сохранения файлов
 *
 * Created by @author AlNat on 12.01.2020.
 * Licensed by Apache License, Version 2.0
 */
public class SimpleFileSystemStorage extends FileSystemStorage {

    public SimpleFileSystemStorage(SystemConfiguration systemConfiguration) {
        super(systemConfiguration);
    }

    @Override
    public void saveFile(byte[] file, String filename) throws UStorageException {
        // TODO Если файл существует - перезатереть его
    }

    @Override
    public byte[] fetchFile(String filename) throws UStorageException {
        return new byte[0];
    }

    @Override
    public void deleteFile(String filename) throws UStorageException {

    }

    @Override
    public List<String> getFileNameList() throws UStorageException {
        return null;
    }

    @Override
    public Map<String, byte[]> getAllFile() throws UStorageException {
        return null;
    }

}
