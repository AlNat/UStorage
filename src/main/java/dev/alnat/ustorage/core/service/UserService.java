package dev.alnat.ustorage.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class UserService {

    private Logger log = LoggerFactory.getLogger(this.getClass());


}
