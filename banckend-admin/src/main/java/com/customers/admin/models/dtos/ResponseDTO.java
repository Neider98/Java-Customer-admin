package com.customers.admin.models.dtos;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ResponseDTO {

    private String status;
    private List<?> message;
    private int code;
    private Date date;

}
