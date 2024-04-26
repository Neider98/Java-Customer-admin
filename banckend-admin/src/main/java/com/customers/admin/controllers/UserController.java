package com.customers.admin.controllers;

import com.customers.admin.facades.UserFacade;
import com.customers.admin.models.dtos.ResponseDTO;
import com.customers.admin.models.dtos.UserDTO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@RestController
@RequestMapping("/api/v1/customers")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    private final UserFacade userFacade;

    @Autowired
    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    /**
     *
     * @return
     */
    @GetMapping("/")
    public ResponseDTO getUsers() {
        ResponseDTO response = new ResponseDTO();
        response.setStatus(HttpStatus.OK.name());
        response.setMessage(userFacade.findAllUsers());
        response.setCode(HttpStatus.OK.value());
        response.setDate(new Date(System.currentTimeMillis()));
        logger.info(String.format("response getUsers(): %s", response));
        return response;
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getUser(@PathVariable Long id) {
        Optional<UserDTO> userOptional = userFacade.findUserById(id);
        List<UserDTO> users = new ArrayList<>();
        if (userOptional.isPresent()) {
            users.add(userOptional.orElseThrow());
            ResponseDTO response = new ResponseDTO();
            response.setStatus(HttpStatus.OK.name());
            response.setMessage(users);
            response.setCode(HttpStatus.OK.value());
            response.setDate(new Date(System.currentTimeMillis()));
            logger.info(String.format("Response getUser: %s", response));
            return ResponseEntity.ok(response);
        }
        logger.error("Response failed: ");
        return ResponseEntity.notFound().build();
    }

    /**
     *
     * @param user
     * @param result
     * @return
     */
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO user,
                                     BindingResult result) {
        if (result.hasFieldErrors()) {
            logger.error("validation failed: %s", validation(result));
            return validation(result);
        }
        logger.info("method create user finished Ok");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userFacade.saveUsers(user));
    }

    /**
     *
     * @param user
     * @param result
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @Valid @RequestBody UserDTO user,
            BindingResult result,
            @PathVariable Long id) {

        if (result.hasFieldErrors()) {
            logger.error(String.format("error validation: %s",
                    validation(result)));
            return validation(result);
        }
        Optional<UserDTO> productOptional = userFacade.updateUser(user);
        if (productOptional.isPresent()) {
            logger.info("updateUser method finished, result: %s",
                    productOptional.orElseThrow());
            return ResponseEntity.status(HttpStatus.CREATED).body(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     *
     * @param response
     * @return
     */
    @GetMapping("/export")
    public ResponseEntity<String> exportUsers(HttpServletResponse response) {
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=clients.csv");

        try {
            List<UserDTO> users = userFacade.findAllUsers();
            PrintWriter writer = response.getWriter();
            logger.info("Write header for file csv");
            writer.write("shared key,business id,phone,email,data added \n");
            logger.info("Write content for file csv");
            for (UserDTO user : users) {
                writer.write(user.getSharedKey() + "," + user.getBusinessId() +
                        "," +
                        user.getPhone() + "," + user.getEmail() + "," +  user.getDateAdded() + "\n");
            }
            writer.flush();
            writer.close();
            logger.info("Write file csv finished");
            return new ResponseEntity<>("Clientes exportados correctamente", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error al exportar el documento", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> errors.put(error.getField(),
                "El campo: " + error.getField() + " " + error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

}
