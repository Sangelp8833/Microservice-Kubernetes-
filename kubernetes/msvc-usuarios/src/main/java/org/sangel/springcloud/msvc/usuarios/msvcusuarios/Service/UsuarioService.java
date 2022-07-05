package org.sangel.springcloud.msvc.usuarios.msvcusuarios.Service;

import org.sangel.springcloud.msvc.usuarios.msvcusuarios.DTO.UsuarioDTO;
import org.sangel.springcloud.msvc.usuarios.msvcusuarios.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> listar();
    Optional<Usuario> porId(Long id);
    Usuario guardar(Usuario usuario);
    Usuario actualizar(UsuarioDTO usuarioDTO, Long id);
    void eliminar(Long id);
}
