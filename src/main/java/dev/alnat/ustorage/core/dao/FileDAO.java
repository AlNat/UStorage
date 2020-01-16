package dev.alnat.ustorage.core.dao;

import dev.alnat.ustorage.core.model.File;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by @author AlNat on 12.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@Repository
@Transactional
public interface FileDAO extends PagingAndSortingRepository<File, Integer> {

    File getByStorageFileName(String storageFileName);
    File getByFilename(String fileName);

}
