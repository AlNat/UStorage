package dev.alnat.ustorage.core.system.filesystem;

import dev.alnat.ustorage.core.model.SystemConfiguration;
import dev.alnat.ustorage.exception.UStorageException;

import java.util.List;
import java.util.Map;

/**
 *
 * TODO Реализовать через корретные буфферизированные варианты
 *
 * Created by @author AlNat on 13.01.2020.
 * Licensed by Apache License, Version 2.0
 */
public class BufferedFileSystemStorage extends FileSystemStorage {

    public BufferedFileSystemStorage(SystemConfiguration systemConfiguration) {
        super(systemConfiguration);
    }


    @Override
    public void saveFile(byte[] file, String filename) throws UStorageException {

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

    /*
            try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFilename), "UTF-8"));
            writer.write(data);
        } catch (IOException e) {
            throw new AxiExportException("Ошибка записи в файл", e);
        }
     */

}
