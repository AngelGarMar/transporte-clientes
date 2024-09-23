package com.transporte.controllers;

import com.transporte.constants.Constants;
import com.transporte.dto.RegisterClientRequest;
import com.transporte.models.ResponseService;
import com.transporte.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
@Slf4j
public class ClienteController {
    @Autowired
    private ClientService clientService;

    @PostMapping("/register")
    @Operation(summary = "register", description = "Guardar un nuevo cliente.", tags = {"transporte-clientes"})
    @Parameter(name = "registerClientRequest", description = "Objeto con los datos del cliente",
        example = "nombre=Cliente1, apaterno=Apaterno1, amaterno=Amaterno1, edad=40, celular=5522334455, tarjeta=1010101010", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente guardado."),
            @ApiResponse(responseCode = "200", description = "El cliente ya existe.")
    })
    //https://www.kranio.io/blog/integra-swagger-con-spring-boot-documenta-tus-apis-restful
    //http://localhost:8796/swagger-ui/index.html
    public ResponseEntity<ResponseService> newClient(@RequestBody @Valid RegisterClientRequest registerClientRequest, BindingResult result) {
        log.info("Llamada a metodo del controller ClienteController::newClient");
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        log.info("Request parameter: {}", registerClientRequest);
        ResponseService response = clientService.newClient(registerClientRequest);
        return new ResponseEntity<ResponseService>(response, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "", description = "Obtiene un listado de clientes.", tags = {"transporte-clientes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "No existen clientes."),
            @ApiResponse(responseCode = "200", description = "Se muestran clientes.")
    })
    public ResponseEntity<ResponseService> getClients(@Value("${server.port}") String port) {
        ResponseService response = clientService.getClientes();
        log.info(port);
        return new ResponseEntity<ResponseService>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "{id}", description = "Obtiene un cliente.", tags = {"transporte-clientes"})
    @Parameter(name = "id", description = "Id del cliente", example = "3", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "No existe el cliente."),
            @ApiResponse(responseCode = "200", description = "El cliente ya existe.")
    })
    public ResponseEntity<ResponseService> getClientById(@PathVariable("id") Long id) {
        ResponseService response = clientService.getCliente(id);
        return new ResponseEntity<ResponseService>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "{id}", description = "Elimina un cliente.", tags = {"transporte-clientes"})
    @Parameter(name = "id", description = "Id del cliente", example = "3", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "No existe el cliente."),
            @ApiResponse(responseCode = "200", description = "El cliente ya se encuentra eliminado."),
            @ApiResponse(responseCode = "200", description = "Cliente eliminado.")
    })
    public ResponseEntity<ResponseService> deleteClient(@PathVariable("id") Long id) {
        log.info("Porudcot eliminado: {}", id);
        ResponseService response = clientService.deleteClient(id);
        log.info("Porudcot eliminado: {}", response.getData());
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
