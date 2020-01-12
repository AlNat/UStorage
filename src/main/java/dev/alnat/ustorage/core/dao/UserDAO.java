package dev.alnat.ustorage.core.dao;

import dev.alnat.ustorage.core.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by @author AlNat on 12.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@Repository
public interface UserDAO extends CrudRepository<User, Integer> {

    User getUserByLogin(String login);

}
