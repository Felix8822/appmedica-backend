package com.appmedica.backend.controller;

import com.appmedica.backend.model.Recordatorio;
import com.appmedica.backend.model.RecordatorioRequest;
import com.appmedica.backend.model.Usuario;
import com.appmedica.backend.repository.RecordatorioRepository;
import com.appmedica.backend.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/recordatorios")
public class RecordatorioController {

    private final RecordatorioRepository recordatorioRepository;
    private final UsuarioRepository usuarioRepository;

    public RecordatorioController(RecordatorioRepository recordatorioRepository, UsuarioRepository usuarioRepository) {
        this.recordatorioRepository = recordatorioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Crear recordatorio usando RecordatorioRequest y asociando el usuario
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody RecordatorioRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId()).orElse(null);
        if (usuario == null) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }

        Recordatorio recordatorio = new Recordatorio();
        recordatorio.setTitulo(request.getTitulo());
        recordatorio.setDescripcion(request.getDescripcion());
        recordatorio.setFecha(LocalDate.parse(request.getFecha())); // yyyy-MM-dd
        recordatorio.setHora(LocalTime.parse(request.getHora()));   // HH:mm
        recordatorio.setUsuario(usuario);

        recordatorioRepository.save(recordatorio);

        return ResponseEntity.ok(recordatorio);
    }

    // Obtener todos o por usuarioId
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
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody RecordatorioRequest request) {
        return recordatorioRepository.findById(id).map(r -> {
            r.setTitulo(request.getTitulo());
            r.setDescripcion(request.getDescripcion());
            r.setFecha(LocalDate.parse(request.getFecha()));
            r.setHora(LocalTime.parse(request.getHora()));

            // Si el usuarioId ha cambiado, actualizar el usuario
            if (request.getUsuarioId() != null && !request.getUsuarioId().equals(r.getUsuario().getId())) {
                Usuario usuario = usuarioRepository.findById(request.getUsuarioId()).orElse(null);
                if (usuario == null) {
                    return ResponseEntity.badRequest().body("Usuario no encontrado");
                }
                r.setUsuario(usuario);
            }

            recordatorioRepository.save(r);
            return ResponseEntity.ok(r);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        recordatorioRepository.deleteById(id);
    }
}
