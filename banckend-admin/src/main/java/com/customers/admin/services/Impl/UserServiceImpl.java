package com.customers.admin.services.Impl;

import com.customers.admin.models.entities.User;
import com.customers.admin.repositories.IUserRepository;
import com.customers.admin.services.IUserService;
import com.customers.admin.util.exceptions.IdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    /**
     *
     * @param userRepository
     */
    @Autowired
    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     *
     * @return
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Optional<User> findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        user.orElseThrow(() -> new IdNotFoundException("users"));
        return user;
    }

    /**
     *
     * @param user
     * @return
     */
    @Override
    public User saveUser(User user) {
        String sharedKey = deleteDomainEmail(user.getEmail());
        user.setSharedKey(sharedKey);
        return userRepository.save(user);
    }

    /**
     *
     * @param user
     * @return
     */
    @Override
    public Optional<User> updateUser(User user) {
        Optional<User> userOptional = userRepository.findById(user.getId());
        if (userOptional.isPresent()) {
            User updatedUser = userRepository.save(user);
            return Optional.of(updatedUser);
        } else {
            return Optional.empty();
        }
    }

    /**
     *
     * @param userId
     * @return
     */
    @Override
    public Optional<User> deleteUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            userRepository.deleteById(userId);
            return userOptional;
        } else {
            return Optional.empty();
        }
    }

    /**
     *
     * @param correo
     * @return
     */
    public static String deleteDomainEmail(String correo) {
        int indiceArroba = correo.indexOf('@');
        if (indiceArroba != -1) {
            return correo.substring(0, indiceArroba);
        } else {
            return correo;
        }
    }

}
