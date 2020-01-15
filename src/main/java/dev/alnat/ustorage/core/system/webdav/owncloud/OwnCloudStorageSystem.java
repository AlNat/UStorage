package dev.alnat.ustorage.core.system.webdav.owncloud;

import dev.alnat.ustorage.core.model.SystemConfiguration;
import dev.alnat.ustorage.core.system.webdav.WebDAVStorageSystem;
import dev.alnat.ustorage.exception.UStorageException;

import java.util.List;
import java.util.Map;

/**
 * Created by @author AlNat on 15.01.2020.
 * Licensed by Apache License, Version 2.0
 */
public class OwnCloudStorageSystem extends WebDAVStorageSystem {

    public OwnCloudStorageSystem(SystemConfiguration systemConfiguration) {
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
}
