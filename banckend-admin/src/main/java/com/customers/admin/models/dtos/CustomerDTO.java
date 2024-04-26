package com.customers.admin.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class CustomerDTO {

    private Long id;    

    private String sharedKey;

    @NotBlank
    private String businessId;

    @NotBlank
    private String phone;

    @NotBlank
    @Email
    private String email;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;

    private LocalDate dateAdded;

}
