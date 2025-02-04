package com.transporte.controllers;

import com.transporte.constants.Constants;
import com.transporte.dto.RegisterClientRequest;
import com.transporte.models.ResponseService;
import com.transporte.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/clients")
public class ClienteController {
    @Autowired
    private ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<ResponseService> newClient(@RequestBody @Valid RegisterClientRequest registerClientRequest, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        ResponseService response = clientService.newClient(registerClientRequest);
        return new ResponseEntity<ResponseService>(response, HttpStatus.OK);
    }

    private ResponseEntity<ResponseService> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return new ResponseEntity<ResponseService>(new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.FALTAN_CAMPOS, errors), HttpStatus.OK);
    }
}
