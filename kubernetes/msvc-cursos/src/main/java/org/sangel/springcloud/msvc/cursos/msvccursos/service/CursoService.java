package org.sangel.springcloud.msvc.cursos.msvccursos.service;


import org.sangel.springcloud.msvc.cursos.msvccursos.DTO.CursoDTO;
import org.sangel.springcloud.msvc.cursos.msvccursos.DTO.Usuario;
import org.sangel.springcloud.msvc.cursos.msvccursos.model.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    List<Curso> listar();
    Optional<Curso> porId(Long id);
    Curso guardar(Curso curso);
    CursoDTO actualizar(CursoDTO cursoDTO, Long id);
    void eliminar(Long id);

    void eliminarCursoUsuario(Long id);

    // <---- Feign ---->
    Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);
    Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);
    Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId);
    Optional<Curso> porIdConUsuarios(Long id);


}
