package dev.alnat.ustorage.core.service;

import dev.alnat.ustorage.core.dao.UserDAO;
import dev.alnat.ustorage.core.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Сервис для взаимодействия с учетными записями пользователей
 *
 * Created by @author AlNat on 08.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@Service
@Transactional
public class UserService implements UserDetailsService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User u = userDAO.getUserByLogin(s);

        if (u == null) {
            throw new UsernameNotFoundException("Username " + s + " not found");
        }

        return u;
    }

}
