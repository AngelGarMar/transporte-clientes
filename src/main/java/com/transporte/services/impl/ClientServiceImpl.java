package com.transporte.services.impl;

import com.transporte.constants.Constants;
import com.transporte.dto.RegisterClientRequest;
import com.transporte.dto.RegisterClientResponse;
import com.transporte.entities.Cliente;
import com.transporte.models.ResponseService;
import com.transporte.repositories.ClientRepository;
import com.transporte.services.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Override
    @Transactional(readOnly = false)
    public ResponseService newClient(RegisterClientRequest registerClientRequest) {
        //Se revisa si existe el nombre del usuario
        Optional<Cliente> clientOp = clientRepository.findByNombreAndApaternoAndAmaternoAndEstatus(
                registerClientRequest.getNombre(), registerClientRequest.getApaterno(), registerClientRequest.getAmaterno(), 1
        );
        if (clientOp.isPresent()) {
            return new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.CLIENTE_EXISTENTE, null);
        }

        Cliente clienteSaved =Cliente.builder()
                .nombre(registerClientRequest.getNombre())
                .apaterno(registerClientRequest.getApaterno())
                .amaterno(registerClientRequest.getAmaterno())
                .credencial("credencial")
                .edad(registerClientRequest.getEdad())
                .insen(registerClientRequest.getInsen())
                .celular(registerClientRequest.getCelular())
                .tarjetaId(registerClientRequest.getTarjetaId())
                .creado(LocalDateTime.now(ZoneId.of("America/Mexico_City")))
                .estatus(1)
                .build();
        clienteSaved = clientRepository.save(clienteSaved);
        RegisterClientResponse clientResponse = new RegisterClientResponse(clienteSaved);
        return new ResponseService(Constants.RESPONSE_TYPE_SUCCESS, Constants.CLIENTE_GUARDADO, clientResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseService getClientes() {
        List<Cliente> clientes = clientRepository.findByEstatus(1);

        if (clientes.isEmpty()) {
            return new ResponseService(Constants.RESPONSE_TYPE_SUCCESS, Constants.CLIENTES_NO_EXISTEN, null);
        }

        List<RegisterClientResponse> clientesResponse = clientes.stream().map(RegisterClientResponse::new).toList();

        return new ResponseService(Constants.RESPONSE_TYPE_SUCCESS, Constants.CLIENTES_EXISTENTES, clientesResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseService getCliente(Long id) {
        Optional<Cliente> cliente = clientRepository.findByIdAndEstatus(id, 1);

        if (cliente.isEmpty()) {
            return new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.CLIENTE_NO_EXISTENTE, null);
        }

        return new ResponseService(Constants.RESPONSE_TYPE_SUCCESS, Constants.CLIENTE_EXISTENTE, new RegisterClientResponse(cliente.get()));
    }

    @Override
    @Transactional(readOnly = false)
    public ResponseService deleteClient(Long id) {
        Optional<Cliente> clienteOp = clientRepository.findById(id);

        if (clienteOp.isEmpty()) {
            return new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.CLIENTE_NO_EXISTENTE, null);
        }

        Cliente cliente = clienteOp.get();

        if (cliente.getEstatus() == 0) {
            return new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.CLIENTE_YA_ELIMINADO, null);
        }

        cliente.setEstatus(0);

        cliente = clientRepository.save(cliente);

        return new ResponseService(Constants.RESPONSE_TYPE_SUCCESS, Constants.CLIENTE_ELIMINADO, new RegisterClientResponse(cliente));
    }
}
