package dev.alnat.ustorage.core.system.s3;

import dev.alnat.ustorage.core.model.SystemConfiguration;
import dev.alnat.ustorage.core.system.AbstractStorageSystem;

/**
 * Created by @author AlNat on 08.01.2020.
 * Licensed by Apache License, Version 2.0
 */
public abstract class AbstractS3StorageSystem extends AbstractStorageSystem {

    public AbstractS3StorageSystem(SystemConfiguration systemConfiguration) {
        super(systemConfiguration);
    }

}
