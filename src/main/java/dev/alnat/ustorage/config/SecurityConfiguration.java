package dev.alnat.ustorage.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Аутентификация для всех запросов
 *
 * Created by @author AlNat on 08.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true) // Включаем конфигурирование аннотациями
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
                .authorizeRequests()
                    .antMatchers("/resources/**").permitAll().and()
                .authorizeRequests() // По /login - пускать всех
                    .antMatchers("/login").permitAll().and()
                .formLogin() // Описываем форму логина
                    .loginPage("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .loginProcessingUrl("/login") // Т.к. https://docs.spring.io/spring-security/site/migrate/current/3-to-4/html5/migrate-3-to-4-xml.html#m3to4-xmlnamespace-form-login
                    .failureForwardUrl("/login?error=true")
                    .permitAll()
                    .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                        // TODO Заллогировать в отдельную таблицу
                        httpServletResponse.setHeader("last_login_in", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                        httpServletResponse.sendRedirect("/web/");
                    })
                    .and()
                .logout() // Выход из учетной записи
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout=true")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .and()
                .authorizeRequests() // API доступен только для админов и API пользователей
                    .antMatchers("/api/**").hasAnyAuthority(RoleHolder.ADMIN, RoleHolder.API).and()
                .authorizeRequests() // UI доступен админам и пользователям
                    .antMatchers("/web/").hasAnyAuthority(RoleHolder.ADMIN, RoleHolder.USER).and()
                .authorizeRequests() // Все остальные запросы - только админ
                    .antMatchers("/**").hasAnyAuthority(RoleHolder.ADMIN);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
