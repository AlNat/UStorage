package dev.alnat.ustorage.core.system.filesystem;

import dev.alnat.ustorage.core.model.SystemConfiguration;
import dev.alnat.ustorage.core.system.AbstractStorageSystem;
import dev.alnat.ustorage.exception.UStorageException;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Общие принципы для хранения в любой фаловой системе
 * Проверяет наличие доступа к указанной директории
 *
 * Конфигурация должна выглядеть следующим образом:
 * {
 *     "path" : "C:/DIR/"
 * }
 *
 * Created by @author AlNat on 12.01.2020.
 * Licensed by Apache License, Version 2.0
 */
public abstract class FileSystemStorage extends AbstractStorageSystem {

    /**
     * Место хранения файлов
     */
    protected volatile Path storagePath;

    public FileSystemStorage(SystemConfiguration systemConfiguration) {
        super(systemConfiguration);
    }


    /**
     * Проверяется возможно читать и писать в указанную директорию
     *
     * @return true если да, false если нет
     */
    @Override
    protected boolean validateConfig() {
        Path path;
        try {
            JSONObject conf = new JSONObject(systemConfiguration.getConfiguration());
            path = Paths.get(conf.getString("path"));
        } catch (JSONException e) {
            return false;
        }

        return Files.exists(path) && Files.isWritable(path) && Files.isReadable(path);
    }

    @Override
    protected void init() throws UStorageException {
        JSONObject conf = new JSONObject(systemConfiguration.getConfiguration());
        this.storagePath = Paths.get(conf.getString("path"));
    }

}
