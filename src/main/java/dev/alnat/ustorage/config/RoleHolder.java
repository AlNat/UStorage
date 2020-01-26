package dev.alnat.ustorage.config;

import dev.alnat.ustorage.core.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Класс-хранилище для текстового представления ролей в Spring Security
 * Т.к. в SpringSecurity трудно убрать префикс ROLE_ у прав пользователя - для лучшего визуального отображения проще вынести
 *  зашитые значения поля в данный класс и обращаться к ним как к переменным.
 *
 * Created by @author AlNat on 23.01.2020.
 * Licensed by Apache License, Version 2.0
 */
public class RoleHolder {

    public static final String ADMIN    = "ROLE_ADMIN";
    public static final String API      = "ROLE_API";
    public static final String USER     = "ROLE_USER";

    private static List<String> roleList;

    /**
     * Через рефлексию находим все поля данного класса для удобства
     * Т.к. при добавлении новой роли велик шанс забыть добавить ее в этот список
     * Т.к. поле static - быстродействие не сильно пострадает
     */
    static {
        roleList = new LinkedList<>();

        // Создаем список полей класса
        Class<?> roleHolderClass = RoleHolder.class;
        for (Field f : roleHolderClass.getFields()) {
            if (java.lang.reflect.Modifier.isStatic(f.getModifiers()) && f.getType() == String.class) {

                // Если это роль - добавляем
                String role;
                try {
                    role = (String) f.get(null);
                } catch (IllegalAccessException e) {
                    continue;
                }

                roleList.add(role);
            }
        }
    }

    /**
     * Метод получения списка ролей пользователя по его сущности
     * Проходим списке всех ролей, объявленных выше, и формируем список прав пользователя
     *
     * В дальнейшем, если вынести права в отдельную таблицу, можно будет пройтись циклом по списку связанных сущностей
     * (например по списку прав всех групп, в которых входит пользователь), сделать этому distinct и затем пройти весь список возмоных ролей, сранивания возмоности
     *
     * @param user пользователь
     * @return набор его прав
     */
    public static Collection<GrantedAuthority> generateAuthoritiesToUser(User user) {
        String compressedRole = user.getRole();
        Collection<GrantedAuthority> authorities = new LinkedList<>();

        for (String role : roleList) {
            if (compressedRole.contains(role)) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }

        return authorities;
    }

}
