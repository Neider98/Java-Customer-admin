package com.customers.admin.services;

import com.customers.admin.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> findAllUsers ();

    Optional<User> findUserById(Long id);

    User saveUser(User user);

    Optional<User> updateUser(User user);

    Optional<User> deleteUser(Long userId);



}
