package com.appmedica.backend.model;

import com.appmedica.backend.model.Alarma;
import com.appmedica.backend.repository.AlarmaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class AlarmaScheduler {

    private final AlarmaRepository alarmaRepository;

    public AlarmaScheduler(AlarmaRepository alarmaRepository) {
        this.alarmaRepository = alarmaRepository;
    }

    // Ejecutar todos los días a las 03:00 AM
    @Scheduled(cron = "0 0 3 * * ?")
    public void borrarAlarmasCaducadas() {
        List<Alarma> caducadas = alarmaRepository.findByFechaBefore(LocalDate.now());

        if (!caducadas.isEmpty()) {
            alarmaRepository.deleteAll(caducadas);
            System.out.println("[AlarmaScheduler] Se eliminaron " + caducadas.size() + " alarmas caducadas.");
        }
    }
}
