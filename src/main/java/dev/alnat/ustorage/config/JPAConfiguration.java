package dev.alnat.ustorage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by @author AlNat on 08.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@EnableJpaRepositories(basePackages = "dev.alnat.ustorage.core.dao")
@Configuration
@EnableTransactionManagement
public class JPAConfiguration {

}
