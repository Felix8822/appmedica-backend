package com.appmedica.backend.controller;

import com.appmedica.backend.model.Medicamento;
import com.appmedica.backend.repository.MedicamentoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoController(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    @PostMapping
    public Medicamento crear(@RequestBody Medicamento medicamento) {
        return medicamentoRepository.save(medicamento);
    }

    @GetMapping
    public List<Medicamento> obtenerTodos() {
        return medicamentoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Medicamento obtenerPorId(@PathVariable Long id) {
        return medicamentoRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Medicamento actualizar(@PathVariable Long id, @RequestBody Medicamento nuevo) {
        return medicamentoRepository.findById(id).map(med -> {
            med.setNombre(nuevo.getNombre());
            med.setDescripcion(nuevo.getDescripcion());
            return medicamentoRepository.save(med);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        medicamentoRepository.deleteById(id);
    }
}
