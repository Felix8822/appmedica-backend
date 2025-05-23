package com.appmedica.backend.repository;

import com.appmedica.backend.model.Alarma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmaRepository extends JpaRepository<Alarma, Long> {
}
