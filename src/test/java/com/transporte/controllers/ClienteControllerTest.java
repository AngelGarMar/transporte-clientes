package com.transporte.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transporte.dto.RegisterClientRequest;
import com.transporte.dto.RegisterClientResponse;
import com.transporte.models.ResponseService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class ClienteControllerTest {
    @Autowired
    private TestRestTemplate client;

    private ObjectMapper objectMapper;

    @LocalServerPort
    private int puerto;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void testNewClient() {
        RegisterClientRequest cliente = new RegisterClientRequest();
        cliente.setNombre("Cliente1");
        cliente.setApaterno("Apaterno1");
        cliente.setAmaterno("Amaterno1");
        cliente.setCelular("5523411690");
        cliente.setEdad(38);
        cliente.setTarjetaId("1010101010");
        ResponseEntity<ResponseService> respuesta = client.postForEntity(crearUri("/register"), cliente, ResponseService.class);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, respuesta.getHeaders().getContentType());
        ResponseService clienteCreado = respuesta.getBody();
        assertNotNull(clienteCreado);
        log.info(clienteCreado.toString());
    }

    private String crearUri(String uri) {
        return "http://localhost:" + puerto + uri;
    }
}