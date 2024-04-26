package com.customers.admin.facades;

import com.customers.admin.mappers.UserMapper;
import com.customers.admin.models.dtos.UserDTO;
import com.customers.admin.models.entities.User;
import com.customers.admin.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserFacade {

    private final IUserService userService;
    private final UserMapper userMapper;

    /**
     *
     * @param userService
     * @param userMapper
     */
    @Autowired
    public UserFacade(IUserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    /**
     *
     * @param id
     * @return
     */
    public Optional<UserDTO> findUserById(Long id) {
        return userMapper.mapToDtoOptional(userService.findUserById(id));
    }

    /**
     *
     * @return
     */
    public List<UserDTO> findAllUsers() {
        return userMapper.mapToDtoList(userService.findAllUsers());
    }

    /**
     *
     * @param user
     * @return
     */
    public UserDTO saveUsers(UserDTO user) {
        User userEntity = userMapper.userDTOToUser(user);
        return userMapper.userToUserDTO(userService.saveUser(userEntity));
    }

    /**
     *
     * @param userDTO
     * @return
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO){
        User userEntity = userMapper.userDTOToUser(userDTO);
        return userMapper.mapToDtoOptional(userService.updateUser(userEntity));
    }

}
