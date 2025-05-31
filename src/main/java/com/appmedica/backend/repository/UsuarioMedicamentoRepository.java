package com.appmedica.backend.repository;


import com.appmedica.backend.model.UsuarioMedicamento;
import com.appmedica.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioMedicamentoRepository extends JpaRepository<UsuarioMedicamento, Long> {
    List<UsuarioMedicamento> findByUsuario(Usuario usuario);
}
