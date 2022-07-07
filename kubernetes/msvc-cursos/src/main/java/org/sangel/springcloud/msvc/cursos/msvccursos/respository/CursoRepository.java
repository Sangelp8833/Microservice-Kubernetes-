package org.sangel.springcloud.msvc.cursos.msvccursos.respository;

import org.sangel.springcloud.msvc.cursos.msvccursos.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Modifying
    @Query("delete from CursoUsuario cu where cu.usuarioId = ?1")
    void deletCursoUsuarioById(Long id);
}
