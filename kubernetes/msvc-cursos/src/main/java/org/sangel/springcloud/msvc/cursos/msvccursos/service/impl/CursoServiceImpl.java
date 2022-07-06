package org.sangel.springcloud.msvc.cursos.msvccursos.service.impl;

import org.modelmapper.ModelMapper;
import org.sangel.springcloud.msvc.cursos.msvccursos.DTO.CursoDTO;
import org.sangel.springcloud.msvc.cursos.msvccursos.mensajes.ExepcionNoEncontrado;
import org.sangel.springcloud.msvc.cursos.msvccursos.model.Curso;
import org.sangel.springcloud.msvc.cursos.msvccursos.respository.CursoRepository;
import org.sangel.springcloud.msvc.cursos.msvccursos.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    private final ModelMapper mapper = new ModelMapper();

    @Override
    public List<Curso> listar() {
        return cursoRepository.findAll();
    }

    @Override
    public Optional<Curso> porId(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    public CursoDTO actualizar(CursoDTO cursoDTO, Long id) {
        Optional<Curso> user = porId(id);
        if(user.isEmpty()){
            throw new ExepcionNoEncontrado("El usuario no ha sido encontrado");
        }else{
            Curso cursoToUpdate = mapper.map(cursoDTO,Curso.class);
            cursoToUpdate.setId(id);
            cursoRepository.save(cursoToUpdate);
        }
        return mapper.map(porId(id),CursoDTO.class);
    }

    @Override
    public void eliminar(Long id) {
        cursoRepository.deleteById(id);
    }
}
