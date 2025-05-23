package com.appmedica.backend.repository;

import com.appmedica.backend.model.Recordatorio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordatorioRepository extends JpaRepository<Recordatorio, Long> {
}
