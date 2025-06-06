package com.appmedica.backend.repository;

import com.appmedica.backend.model.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
    Optional<Medicamento> findByNombreIgnoreCase(String nombre);
}
