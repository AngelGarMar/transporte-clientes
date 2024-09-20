package com.transporte.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transporte.constants.Constants;
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

    @Test
    @Order(2)
    void testGetClients() {
        ResponseEntity<ResponseService> respuesta = client.getForEntity(crearUri(""), ResponseService.class);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, respuesta.getHeaders().getContentType());
        assertNotNull(respuesta.getBody());
        ResponseService response = respuesta.getBody();
        assertNotNull(response.getData());
    }

    @Test
    @Order(3)
    void testGetClientById() throws JsonProcessingException {
        ResponseEntity<ResponseService> respuesta = client.getForEntity(crearUri("/5"), ResponseService.class);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, respuesta.getHeaders().getContentType());
        ResponseService response = respuesta.getBody();
        assertNotNull(response);
        log.info(response.toString());
        assertNotNull(response.getData());
        JsonNode json = objectMapper.readTree(objectMapper.writeValueAsString(response));
        assertEquals("Cliente1", json.path("data").path("nombre").asText());
        assertEquals("Apaterno1", json.path("data").path("apaterno").asText());
        assertEquals("5523411690", json.path("data").path("celular").asText());
    }

    @Test
    @Order(4)
    void testDeleteClient() throws JsonProcessingException {
        client.delete(crearUri("/4"));
        ResponseEntity<ResponseService> respuesta = client.getForEntity(crearUri("/4"), ResponseService.class);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, respuesta.getHeaders().getContentType());
        ResponseService response = respuesta.getBody();
        assertNotNull(response);
        JsonNode json = objectMapper.readTree(objectMapper.writeValueAsString(response));
        assertEquals(Constants.RESPONSE_TYPE_ERROR, json.path("responseType").asText());
        assertEquals(Constants.CLIENTE_NO_EXISTENTE, json.path("message").asText());
    }

    private String crearUri(String uri) {
        return "http://localhost:" + puerto + uri;
    }
}