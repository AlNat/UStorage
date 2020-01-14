package dev.alnat.ustorage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

/**
 * Created by @author AlNat on 08.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@EnableJpaRepositories(basePackages = "dev.alnat.ustorage.core.dao")
@Configuration
@EnableTransactionManagement
public class JPAConfiguration {

    // Для того чтобы перезатереть бин нужно в конфигурации выставить spring.main.allow-bean-definition-overriding=true
    @Bean
    public TransactionManager transactionManager(EntityManagerFactory factory){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(factory);
        return transactionManager;
    }

}
