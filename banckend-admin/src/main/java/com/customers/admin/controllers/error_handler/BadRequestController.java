package com.customers.admin.controllers.error_handler;

import com.customers.admin.models.dtos.ResponseDTO;
import com.customers.admin.util.exceptions.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestController {

    /**
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleIdNotFound(IdNotFoundException exception){
        List<String> message = new ArrayList<>();
        message.add(exception.getMessage());
        ResponseDTO err = new ResponseDTO();
        err.setDate(new Date());
        err.setMessage(message);
        err.setStatus(HttpStatus.BAD_REQUEST.name());
        err.setCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(err);
    }
}
