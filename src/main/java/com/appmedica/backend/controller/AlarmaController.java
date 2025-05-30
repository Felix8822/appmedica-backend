package com.appmedica.backend.controller;

import com.appmedica.backend.model.Alarma;
import com.appmedica.backend.model.AlarmaRequest;
import com.appmedica.backend.model.Medicamento;
import com.appmedica.backend.model.Usuario;
import com.appmedica.backend.repository.AlarmaRepository;
import com.appmedica.backend.repository.MedicamentoRepository;
import com.appmedica.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alarmas")
public class AlarmaController {

    private final AlarmaRepository alarmaRepository;
    private final UsuarioRepository usuarioRepository;
    private final MedicamentoRepository medicamentoRepository;

    @Autowired
    public AlarmaController(
            AlarmaRepository alarmaRepository,
            UsuarioRepository usuarioRepository,
            MedicamentoRepository medicamentoRepository
    ) {
        this.alarmaRepository = alarmaRepository;
        this.usuarioRepository = usuarioRepository;
        this.medicamentoRepository = medicamentoRepository;
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
    @PostMapping
    public ResponseEntity<?> crearAlarma(@RequestBody AlarmaRequest request) {
        Optional<Usuario> usuario = usuarioRepository.findById(request.getUsuarioId());
        Optional<Medicamento> medicamento = medicamentoRepository.findById(request.getMedicamentoId());

        if (usuario.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");
        if (medicamento.isEmpty()) return ResponseEntity.badRequest().body("Medicamento no encontrado");

        Alarma alarma = new Alarma();
        alarma.setTitulo(request.getTitulo());
        alarma.setFecha(LocalDate.parse(request.getFecha()));
        alarma.setHora(LocalTime.parse(request.getHora()));
        alarma.setUsuario(usuario.get());
        alarma.setMedicamento(medicamento.get());

        alarmaRepository.save(alarma);
        return ResponseEntity.ok("Alarma guardada correctamente");
    }

}
