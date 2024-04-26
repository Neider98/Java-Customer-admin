package com.customers.admin.services.Impl;

import com.customers.admin.models.entities.User;
import com.customers.admin.repositories.IUserRepository;
import com.customers.admin.services.Impl.UserServiceImpl;
import com.customers.admin.util.exceptions.IdNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(new User()));

        assertEquals(1, userService.findAllUsers().size());
    }

    @Test
    public void testFindUserById() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findUserById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    public void testFindUserById_IdNotFoundException() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> userService.findUserById(1L));
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setId(1L);
        user.setSharedKey("user");
        user.setPhone("123");
        user.setEmail("user@example.com");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertEquals(1L, savedUser.getId());
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        Optional<User> updatedUser = userService.updateUser(user);

        assertTrue(updatedUser.isPresent());
        assertEquals(1L, updatedUser.get().getId());
    }

    @Test
    public void testUpdateUser_UserNotFound() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        Optional<User> updatedUser = userService.updateUser(new User());

        assertFalse(updatedUser.isPresent());
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> deletedUser = userService.deleteUser(1L);

        assertTrue(deletedUser.isPresent());
        assertEquals(1L, deletedUser.get().getId());
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteUser_UserNotFound() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        Optional<User> deletedUser = userService.deleteUser(1L);

        assertFalse(deletedUser.isPresent());
        verify(userRepository, never()).deleteById(any());
    }
}
