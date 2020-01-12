package dev.alnat.ustorage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * Java конфиг для SpringFox (Swagger)
 *
 * Created by @author AlNat on 12.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2) // Тип документации
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(io.swagger.annotations.Api.class)) // Условие документирования контроллеров
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    /**
     * Создания описания заголовка документации
     *
     * @return созданный класс для этого
     */
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "UStorage API docs",
                "Документация API для продукта UStorage",
                "1.0",
                "Terms of service",
                new Contact("AlNat", "https://cv.alnat.dev/", "aleksnatarov@gmail.com"),
                "Apache License, Version 2.0",
                "https://github.com/AlNat/UStorage/blob/master/LICENSE",
                Collections.emptyList());
    }

}
