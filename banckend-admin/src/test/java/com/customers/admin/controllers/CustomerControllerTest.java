package com.customers.admin.controllers;

import com.customers.admin.models.dtos.CustomerDTO;
import com.customers.admin.services.facades.CustomerFacade;
import com.customers.admin.models.dtos.ResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CustomerControllerTest {

    @Mock
    private CustomerFacade customerFacade;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private HttpServletResponse httpServletResponse;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCustomers() {
        when(customerFacade.findAllCustomers()).thenReturn(Collections.singletonList(new CustomerDTO()));

        ResponseDTO response = customerController.getCustomers();

        assertEquals(HttpStatus.OK.name(), response.getStatus());
        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertEquals(1, response.getMessage().size());
    }

    @Test
    public void testGetCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        Optional<CustomerDTO> userOptional = Optional.of(customerDTO);
        when(customerFacade.findCustomerById(anyLong())).thenReturn(userOptional);

        ResponseEntity<ResponseDTO> responseEntity = customerController.getCustomer(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().getMessage().size());
    }

    @Test
    public void testCreateCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        when(bindingResult.hasFieldErrors()).thenReturn(false);
        when(customerFacade.saveCustomers(any(CustomerDTO.class))).thenReturn(customerDTO);

        ResponseEntity<?> responseEntity = customerController.createCustomer(customerDTO, bindingResult);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        Optional<CustomerDTO> userOptional = Optional.of(customerDTO);
        when(bindingResult.hasFieldErrors()).thenReturn(false);
        when(customerFacade.updateCustomers(any(CustomerDTO.class))).thenReturn(userOptional);

        ResponseEntity<?> responseEntity = customerController.updateCustomer(customerDTO, bindingResult, 1L);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testExportCustomers() throws Exception {
        // Mock data
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setSharedKey("jmau");
        customerDTO.setBusinessId("juan");
        customerDTO.setPhone("55555");
        customerDTO.setEmail("jmau@gmail.com");
        customerDTO.setDateAdded(LocalDate.now());
        List<CustomerDTO> users = Collections.singletonList(customerDTO);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        // Mock behavior
        when(customerFacade.findAllCustomers()).thenReturn(users);
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        // Invoke method
        ResponseEntity<String> responseEntity = customerController.exportCustomers(httpServletResponse);

        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Clientes exportados correctamente", responseEntity.getBody());
    }

    @Test
    public void testValidation() {
        BindingResult mockBindingResult = mock(BindingResult.class);
        when(mockBindingResult.hasFieldErrors()).thenReturn(true);

        ResponseEntity<?> response =
                CustomerController.validation(mockBindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
