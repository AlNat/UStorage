package dev.alnat.ustorage.config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by @author AlNat on 08.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@EnableJpaRepositories(basePackages = "dev.alnat.ustorage.core.dao")
public class JPAConfiguration {
}
