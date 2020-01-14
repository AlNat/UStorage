package dev.alnat.ustorage.core.system.filesystem;

import dev.alnat.ustorage.core.model.SystemConfiguration;
import dev.alnat.ustorage.exception.UStorageException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        File savingFile = generateFileFromFilename(filename);
        if (savingFile.exists()) {
            log.warn("Файл по пути {} уже существует!", savingFile.getAbsolutePath());
        }

        try {
            Files.write(savingFile.toPath(), file);
        } catch (IOException e) {
            throw new UStorageException(e);
        }
    }

    @Override
    public byte[] fetchFile(String filename) throws UStorageException {
        try {
            return Files.readAllBytes(generateFileFromFilename(filename).toPath());
        } catch (IOException e) {
            throw new UStorageException(e);
        }
    }

    @Override
    public void deleteFile(String filename) throws UStorageException {
        try {
            Files.delete(generateFileFromFilename(filename).toPath());
        } catch (IOException e) {
            throw new UStorageException(e);
        }
    }

    @Override
    public List<String> getFileNameList() throws UStorageException {
        try (Stream<Path> walk = Files.walk(storagePath)) {
            return walk
                    .filter(Files::isRegularFile)
                    .map(file -> file.getFileName().toString())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new UStorageException(e);
        }
    }


    @Override
    public Map<String, byte[]> getAllFile() throws UStorageException {
        try (Stream<Path> walk = Files.walk(storagePath)) {
            return walk
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toMap(
                            fileKey -> fileKey.getFileName().toString(),
                            file -> {
                                try {
                                    return Files.readAllBytes(file);
                                } catch (IOException e) {
                                    log.error("Невозможно прочитать файл {} - он будет пропущен!", file.getFileName());
                                    return null; // TODO Подумать как удалить этот ключ
                                }
                            }
                    ));
        } catch (IOException e) {
            throw new UStorageException(e);
        }
    }

}
