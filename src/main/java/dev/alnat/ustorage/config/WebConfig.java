package dev.alnat.ustorage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * If you want to keep Spring Boot MVC features, and you just want to
 * add additional MVC configuration (interceptors, formatters, view controllers etc.)
 * you can add your own @Configuration class of type WebMvcConfigurerAdapter, but without @EnableWebMvc.
 *
 * If you wish to provide custom instances of RequestMappingHandlerMapping, RequestMappingHandlerAdapter
 * or ExceptionHandlerExceptionResolver you can declare a WebMvcRegistrationsAdapter instance providing such components.
 *
 * Created by @author AlNat on 26.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@Configuration
//@EnableWebMvc
public class WebConfig {

//    @Bean
//    public CommonsMultipartResolver multipartResolver() {
//        MultipartResolver multipartResolver = new CommonsMultipartResolver();
//
//    }

}
