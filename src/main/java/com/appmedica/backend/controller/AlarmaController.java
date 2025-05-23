package com.appmedica.backend.controller;

import com.appmedica.backend.model.Alarma;
import com.appmedica.backend.repository.AlarmaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alarmas")
public class AlarmaController {

    private final AlarmaRepository alarmaRepository;

    public AlarmaController(AlarmaRepository alarmaRepository) {
        this.alarmaRepository = alarmaRepository;
    }

    @PostMapping
    public Alarma crear(@RequestBody Alarma alarma) {
        return alarmaRepository.save(alarma);
    }

    @GetMapping
    public List<Alarma> obtenerTodas() {
        return alarmaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Alarma obtenerPorId(@PathVariable Long id) {
        return alarmaRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Alarma actualizar(@PathVariable Long id, @RequestBody Alarma nueva) {
        return alarmaRepository.findById(id).map(a -> {
            a.setTitulo(nueva.getTitulo());
            a.setFecha(nueva.getFecha());
            a.setHora(nueva.getHora());
            a.setUsuario(nueva.getUsuario());
            a.setMedicamento(nueva.getMedicamento());
            return alarmaRepository.save(a);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        alarmaRepository.deleteById(id);
    }
}
