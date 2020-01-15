package dev.alnat.ustorage.core.system.webdav;

import dev.alnat.ustorage.core.model.SystemConfiguration;
import dev.alnat.ustorage.core.system.AbstractStorageSystem;

/**
 * Created by @author AlNat on 15.01.2020.
 * Licensed by Apache License, Version 2.0
 */
public abstract class WebDAVStorageSystem extends AbstractStorageSystem {

    public WebDAVStorageSystem(SystemConfiguration systemConfiguration) {
        super(systemConfiguration);
    }

}
