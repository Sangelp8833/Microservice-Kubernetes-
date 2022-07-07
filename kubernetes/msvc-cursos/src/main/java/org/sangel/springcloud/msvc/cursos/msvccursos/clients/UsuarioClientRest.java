package org.sangel.springcloud.msvc.cursos.msvccursos.clients;

import org.sangel.springcloud.msvc.cursos.msvccursos.DTO.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "msvc-usuarios", url = "localhost:8001/usuario-feign")
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    Usuario porId(@PathVariable Long id);

    @PostMapping("/")
    Usuario registrarUsuario(@RequestBody Usuario usuario);

    @GetMapping("/usuarios-curso")
    List<Usuario> obtenerAlumnosPorCurso(@RequestParam Iterable<Long> ids);
}
