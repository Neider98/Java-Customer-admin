package com.customers.admin.controllers;

import com.customers.admin.models.dtos.CustomerDTO;
import com.customers.admin.services.facades.CustomerFacade;
import com.customers.admin.models.dtos.ResponseDTO;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customers")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

    private static final Logger logger = LogManager.getLogger(CustomerController.class);

    private final CustomerFacade customerFacade;

    @Autowired
    public CustomerController(CustomerFacade customerFacade) {
        this.customerFacade = customerFacade;
    }

    /**
     *
     * @return
     */
    @GetMapping("/")
    public ResponseDTO getCustomers() {
        ResponseDTO response = new ResponseDTO();
        response.setStatus(HttpStatus.OK.name());
        response.setMessage(customerFacade.findAllCustomers());
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
    public ResponseEntity<ResponseDTO> getCustomer(@PathVariable Long id) {
        Optional<CustomerDTO> userOptional = customerFacade.findCustomerById(id);
        List<CustomerDTO> customers = new ArrayList<>();
        if (userOptional.isPresent()) {
            customers.add(userOptional.orElseThrow());
            ResponseDTO response = new ResponseDTO();
            response.setStatus(HttpStatus.OK.name());
            response.setMessage(customers);
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
     * @param customer
     * @param result
     * @return
     */
    @PostMapping
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerDTO customer,
                                            BindingResult result) {
        logger.info("Create customer", customer);
        if (result.hasFieldErrors()) {
            logger.error("validation failed: %s", validation(result));
            return validation(result);
        }
        logger.info("method create customer finished Ok");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerFacade.saveCustomers(customer));
    }

    /**
     *
     * @param customer
     * @param result
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(
            @Valid @RequestBody CustomerDTO customer,
            BindingResult result,
            @PathVariable Long id) {

        if (result.hasFieldErrors()) {
            logger.error(String.format("error validation: %s",
                    validation(result)));
            return validation(result);
        }
        Optional<CustomerDTO> productOptional = customerFacade.updateCustomers(customer);
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
    public ResponseEntity<String> exportCustomers(HttpServletResponse response) {
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=clients.csv");
        try {
            List<CustomerDTO> customer = customerFacade.findAllCustomers();
            PrintWriter writer = response.getWriter();
            logger.info("Write header for file csv");
            writer.write("shared key,business id,phone,email,data added \n");
            logger.info("Write content for file csv");
            for (CustomerDTO user : customer) {
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
