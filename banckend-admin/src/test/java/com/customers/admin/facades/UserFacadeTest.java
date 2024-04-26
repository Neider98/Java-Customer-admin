package com.customers.admin.facades;

import com.customers.admin.mappers.UserMapper;
import com.customers.admin.models.dtos.UserDTO;
import com.customers.admin.models.entities.User;
import com.customers.admin.services.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserFacadeTest {

    @Mock
    private IUserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserFacade userFacade;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindUserById() {
        User userEntity = new User();
        userEntity.setId(1L);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        when(userService.findUserById(1L)).thenReturn(Optional.of(userEntity));
        when(userMapper.mapToDtoOptional(any())).thenReturn(Optional.of(userDTO));

        Optional<UserDTO> result = userFacade.findUserById(1L);

        assertEquals(userDTO.getId(), result.get().getId());
    }

    @Test
    public void testFindAllUsers() {
        User userEntity = new User();
        userEntity.setId(1L);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        when(userService.findAllUsers()).thenReturn(Collections.singletonList(userEntity));
        when(userMapper.mapToDtoList(Collections.singletonList(userEntity))).thenReturn(Collections.singletonList(userDTO));

        assertEquals(1, userFacade.findAllUsers().size());
    }

    @Test
    public void testSaveUsers() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        User userEntity = new User();
        userEntity.setId(1L);
        when(userMapper.userDTOToUser(userDTO)).thenReturn(userEntity);
        when(userService.saveUser(userEntity)).thenReturn(userEntity);
        when(userMapper.userToUserDTO(userEntity)).thenReturn(userDTO);

        assertEquals(userDTO.getId(), userFacade.saveUsers(userDTO).getId());
    }

    @Test
    public void testUpdateCustomer() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        User userEntity = new User();
        userEntity.setId(1L);
        when(userMapper.userDTOToUser(userDTO)).thenReturn(userEntity);
        when(userService.updateUser(userEntity)).thenReturn(Optional.of(userEntity));
        when(userMapper.mapToDtoOptional(Optional.of(userEntity))).thenReturn(Optional.of(userDTO));

        assertEquals(userDTO.getId(),
                userFacade.updateUser(userDTO).get().getId());
    }
}
