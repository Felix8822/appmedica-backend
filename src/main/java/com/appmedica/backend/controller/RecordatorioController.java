package com.appmedica.backend.controller;

import com.appmedica.backend.model.Recordatorio;
import com.appmedica.backend.repository.RecordatorioRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recordatorios")
public class RecordatorioController {

    private final RecordatorioRepository recordatorioRepository;

    public RecordatorioController(RecordatorioRepository recordatorioRepository) {
        this.recordatorioRepository = recordatorioRepository;
    }

    @PostMapping
    public Recordatorio crear(@RequestBody Recordatorio recordatorio) {
        return recordatorioRepository.save(recordatorio);
    }

    @GetMapping
    public List<Recordatorio> obtenerTodos(@RequestParam(required = false) Long usuarioId) {
        if (usuarioId != null) {
            return recordatorioRepository.findByUsuarioId(usuarioId);
        }
        return recordatorioRepository.findAll();
    }

    @GetMapping("/{id}")
    public Recordatorio obtenerPorId(@PathVariable Long id) {
        return recordatorioRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Recordatorio actualizar(@PathVariable Long id, @RequestBody Recordatorio nuevo) {
        return recordatorioRepository.findById(id).map(r -> {
            r.setTitulo(nuevo.getTitulo());
            r.setDescripcion(nuevo.getDescripcion());
            r.setFecha(nuevo.getFecha());
            r.setHora(nuevo.getHora());
            r.setUsuario(nuevo.getUsuario());
            return recordatorioRepository.save(r);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        recordatorioRepository.deleteById(id);
    }
}
