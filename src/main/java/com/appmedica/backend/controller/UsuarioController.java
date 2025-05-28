package com.appmedica.backend.controller;

import com.appmedica.backend.model.LoginRequest;
import com.appmedica.backend.model.Usuario;
import com.appmedica.backend.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/usuarios")
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String contrasenaCifrada = encoder.encode(usuario.getContrasena());
        usuario.setContrasena(contrasenaCifrada);
        return usuarioRepository.save(usuario);
    }

    @GetMapping("/usuarios")
    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/usuarios/{id}")
    public Usuario obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @PutMapping("/usuarios/{id}")
    public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setApellidos(usuarioActualizado.getApellidos());
            usuario.setCorreoElectronico(usuarioActualizado.getCorreoElectronico());
            usuario.setContrasena(usuarioActualizado.getContrasena());
            usuario.setFechaNacimiento(usuarioActualizado.getFechaNacimiento());
            usuario.setTelefono(usuarioActualizado.getTelefono());
            return usuarioRepository.save(usuario);
        }).orElse(null);
    }

    @DeleteMapping("/usuarios/{id}")
    public void borrarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByCorreoElectronico(loginRequest.getCorreoElectronico());

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            if (encoder.matches(loginRequest.getContrasena(), usuario.getContrasena())) {
                return ResponseEntity.ok(usuario); // ✅ login correcto
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }


}