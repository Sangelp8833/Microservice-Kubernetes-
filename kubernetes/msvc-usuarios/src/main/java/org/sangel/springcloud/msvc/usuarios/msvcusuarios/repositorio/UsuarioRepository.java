package org.sangel.springcloud.msvc.usuarios.msvcusuarios.repositorio;

import org.sangel.springcloud.msvc.usuarios.msvcusuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
}
