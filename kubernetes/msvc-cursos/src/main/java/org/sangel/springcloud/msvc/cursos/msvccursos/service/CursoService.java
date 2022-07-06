package org.sangel.springcloud.msvc.cursos.msvccursos.service;


import org.sangel.springcloud.msvc.cursos.msvccursos.DTO.CursoDTO;
import org.sangel.springcloud.msvc.cursos.msvccursos.model.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    List<Curso> listar();
    Optional<Curso> porId(Long id);
    Curso guardar(Curso curso);
    CursoDTO actualizar(CursoDTO cursoDTO, Long id);
    void eliminar(Long id);

}
