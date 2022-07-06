package org.sangel.springcloud.msvc.cursos.msvccursos.respository;

import org.sangel.springcloud.msvc.cursos.msvccursos.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
}
