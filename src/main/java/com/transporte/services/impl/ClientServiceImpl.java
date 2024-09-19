package com.transporte.services.impl;

import com.transporte.constants.Constants;
import com.transporte.dto.RegisterClientRequest;
import com.transporte.entities.Cliente;
import com.transporte.models.ResponseService;
import com.transporte.repositories.ClientRepository;
import com.transporte.services.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        return null;
    }
}
