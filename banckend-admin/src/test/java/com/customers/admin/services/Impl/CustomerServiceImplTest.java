package com.customers.admin.services.Impl;

import com.customers.admin.models.entities.Customer;
import com.customers.admin.repositories.ICustomerRepository;
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

public class CustomerServiceImplTest {

    @Mock
    private ICustomerRepository userRepository;

    @InjectMocks
    private CustomerServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllCustomer() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(new Customer()));

        assertEquals(1, userService.findAllCustomer().size());
    }

    @Test
    public void testFindCustomerById() {
        Customer customer = new Customer();
        customer.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(customer));

        Optional<Customer> result = userService.findCustomerById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    public void testFindCustomerById_IdNotFoundException() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> userService.findCustomerById(1L));
    }

    @Test
    public void testCustomerUser() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setSharedKey("user");
        customer.setPhone("123");
        customer.setEmail("user@example.com");
        when(userRepository.save(any(Customer.class))).thenReturn(customer);

        Customer savedCustomer = userService.customerUser(customer);

        assertEquals(1L, savedCustomer.getId());
    }

    @Test
    public void testUpdateCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(userRepository.save(any(Customer.class))).thenReturn(customer);

        Optional<Customer> updatedUser = userService.updateCustomer(customer);

        assertTrue(updatedUser.isPresent());
        assertEquals(1L, updatedUser.get().getId());
    }

    @Test
    public void testUpdateUser_CustomerNotFound() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        Optional<Customer> updatedUser = userService.updateCustomer(new Customer());

        assertFalse(updatedUser.isPresent());
    }

    @Test
    public void testDeleteCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(customer));

        Optional<Customer> deletedUser = userService.deleteCustomer(1L);

        assertTrue(deletedUser.isPresent());
        assertEquals(1L, deletedUser.get().getId());
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteUser_CustomerNotFound() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        Optional<Customer> deletedUser = userService.deleteCustomer(1L);

        assertFalse(deletedUser.isPresent());
        verify(userRepository, never()).deleteById(any());
    }
}
