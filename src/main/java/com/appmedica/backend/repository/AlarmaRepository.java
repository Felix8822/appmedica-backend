package com.appmedica.backend.repository;

import com.appmedica.backend.model.Alarma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AlarmaRepository extends JpaRepository<Alarma, Long> {
    // AlarmaRepository.java
    List<Alarma> findByFechaBefore(LocalDate fecha);
}
