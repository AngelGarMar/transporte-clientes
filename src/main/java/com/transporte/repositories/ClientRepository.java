package com.transporte.repositories;

import com.transporte.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByNombreAndApaternoAndAmaternoAndEstatus(String nombre, String apaterno, String amaterno, int status);
    List<Cliente> findByEstatus(int estatus);
    Optional<Cliente> findByIdAndEstatus(Long id, int estatus);
}
