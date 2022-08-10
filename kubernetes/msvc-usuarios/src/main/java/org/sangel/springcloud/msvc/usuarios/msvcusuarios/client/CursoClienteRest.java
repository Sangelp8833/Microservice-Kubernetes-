package org.sangel.springcloud.msvc.usuarios.msvcusuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cursos", url = "host.docker.internal:8002/curso")
public interface CursoClienteRest {

    @DeleteMapping("/borrar-usuario/{usuarioId}")
    void eliminarCursoUsuario(@PathVariable Long usuarioId);
}
