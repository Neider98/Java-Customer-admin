package com.customers.admin.facades;

import com.customers.admin.mappers.CustomerMapper;
import com.customers.admin.models.dtos.CustomerDTO;
import com.customers.admin.models.entities.Customer;
import com.customers.admin.services.ICustomerService;
import com.customers.admin.services.facades.CustomerFacade;
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

public class CustomerFacadeTest {

    @Mock
    private ICustomerService userService;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerFacade customerFacade;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindCustomerById() {
        Customer customerEntity = new Customer();
        customerEntity.setId(1L);
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        when(userService.findCustomerById(1L)).thenReturn(Optional.of(customerEntity));
        when(customerMapper.mapToDtoOptional(any())).thenReturn(Optional.of(customerDTO));

        Optional<CustomerDTO> result = customerFacade.findCustomerById(1L);

        assertEquals(customerDTO.getId(), result.get().getId());
    }

    @Test
    public void testFindAllCustomers() {
        Customer customerEntity = new Customer();
        customerEntity.setId(1L);
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        when(userService.findAllCustomer()).thenReturn(Collections.singletonList(customerEntity));
        when(customerMapper.mapToDtoList(Collections.singletonList(customerEntity))).thenReturn(Collections.singletonList(customerDTO));

        assertEquals(1, customerFacade.findAllCustomers().size());
    }

    @Test
    public void testSaveCustomers() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        Customer customerEntity = new Customer();
        customerEntity.setId(1L);
        when(customerMapper.customerDTOToCustomer(customerDTO)).thenReturn(customerEntity);
        when(userService.customerUser(customerEntity)).thenReturn(customerEntity);
        when(customerMapper.customerToCustomerDTO(customerEntity)).thenReturn(customerDTO);

        assertEquals(customerDTO.getId(), customerFacade.saveCustomers(customerDTO).getId());
    }

    @Test
    public void testUpdateCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        Customer customerEntity = new Customer();
        customerEntity.setId(1L);
        when(customerMapper.customerDTOToCustomer(customerDTO)).thenReturn(customerEntity);
        when(userService.updateCustomer(customerEntity)).thenReturn(Optional.of(customerEntity));
        when(customerMapper.mapToDtoOptional(Optional.of(customerEntity))).thenReturn(Optional.of(customerDTO));

        assertEquals(customerDTO.getId(),
                customerFacade.updateCustomers(customerDTO).get().getId());
    }
}
