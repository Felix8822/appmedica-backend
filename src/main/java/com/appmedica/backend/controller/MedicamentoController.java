// MedicamentoController.java
package com.appmedica.backend.controller;

import com.appmedica.backend.model.Medicamento;
import com.appmedica.backend.model.Usuario;
import com.appmedica.backend.model.UsuarioMedicamento;
import com.appmedica.backend.repository.MedicamentoRepository;
import com.appmedica.backend.repository.UsuarioMedicamentoRepository;
import com.appmedica.backend.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    private final MedicamentoRepository medicamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMedicamentoRepository usuarioMedicamentoRepository;

    public MedicamentoController(MedicamentoRepository medicamentoRepository,
                                 UsuarioRepository usuarioRepository,
                                 UsuarioMedicamentoRepository usuarioMedicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioMedicamentoRepository = usuarioMedicamentoRepository;
    }

    @PostMapping("/{usuarioId}")
    public ResponseEntity<Void> crearMedicamento(@PathVariable Long usuarioId, @RequestBody Medicamento medicamento) {
        medicamentoRepository.save(medicamento);

        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario != null) {
            UsuarioMedicamento rel = new UsuarioMedicamento();
            rel.setUsuario(usuario);
            rel.setMedicamento(medicamento);
            usuarioMedicamentoRepository.save(rel);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Medicamento> obtenerMedicamentosPorUsuario(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario == null) return List.of();

        return usuarioMedicamentoRepository.findByUsuario(usuario)
                .stream()
                .map(UsuarioMedicamento::getMedicamento)
                .toList();
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

    @GetMapping
    public List<Medicamento> listarMedicamentos() {
        return medicamentoRepository.findAll();
    }
}
