package com.customers.admin.controllers;

import com.customers.admin.facades.UserFacade;
import com.customers.admin.models.dtos.ResponseDTO;
import com.customers.admin.models.dtos.UserDTO;
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

public class UserControllerTest {

    @Mock
    private UserFacade userFacade;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private HttpServletResponse httpServletResponse;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUsers() {
        when(userFacade.findAllUsers()).thenReturn(Collections.singletonList(new UserDTO()));

        ResponseDTO response = userController.getUsers();

        assertEquals(HttpStatus.OK.name(), response.getStatus());
        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertEquals(1, response.getMessage().size());
    }

    @Test
    public void testGetUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        Optional<UserDTO> userOptional = Optional.of(userDTO);
        when(userFacade.findUserById(anyLong())).thenReturn(userOptional);

        ResponseEntity<ResponseDTO> responseEntity = userController.getUser(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().getMessage().size());
    }

    @Test
    public void testCreateUser() {
        UserDTO userDTO = new UserDTO();
        when(bindingResult.hasFieldErrors()).thenReturn(false);
        when(userFacade.saveUsers(any(UserDTO.class))).thenReturn(userDTO);

        ResponseEntity<?> responseEntity = userController.createUser(userDTO, bindingResult);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        Optional<UserDTO> userOptional = Optional.of(userDTO);
        when(bindingResult.hasFieldErrors()).thenReturn(false);
        when(userFacade.updateUser(any(UserDTO.class))).thenReturn(userOptional);

        ResponseEntity<?> responseEntity = userController.updateUser(userDTO, bindingResult, 1L);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testExportUsers() throws Exception {
        // Mock data
        UserDTO userDTO = new UserDTO();
        userDTO.setSharedKey("jmau");
        userDTO.setBusinessId("juan");
        userDTO.setPhone("55555");
        userDTO.setEmail("jmau@gmail.com");
        userDTO.setDateAdded(LocalDate.now());
        List<UserDTO> users = Collections.singletonList(userDTO);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        // Mock behavior
        when(userFacade.findAllUsers()).thenReturn(users);
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        // Invoke method
        ResponseEntity<String> responseEntity = userController.exportUsers(httpServletResponse);

        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Clientes exportados correctamente", responseEntity.getBody());
    }

    @Test
    public void testValidation() {
        BindingResult mockBindingResult = mock(BindingResult.class);
        when(mockBindingResult.hasFieldErrors()).thenReturn(true);

        ResponseEntity<?> response =
                UserController.validation(mockBindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
