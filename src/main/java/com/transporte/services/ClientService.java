package com.transporte.services;

import com.transporte.dto.RegisterClientRequest;
import com.transporte.models.ResponseService;

public interface ClientService {
    ResponseService newClient(RegisterClientRequest registerClientRequest);

    ResponseService getClientes();

    ResponseService getCliente(Long id);

    ResponseService deleteClient(Long id);
}
