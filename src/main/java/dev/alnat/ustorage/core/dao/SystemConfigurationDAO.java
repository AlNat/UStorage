package dev.alnat.ustorage.core.dao;

import dev.alnat.ustorage.core.model.SystemConfiguration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by @author AlNat on 08.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@Repository
public interface SystemConfigurationDAO extends CrudRepository<SystemConfiguration, Integer> {

    SystemConfiguration getByKey(String key);

}
