package org.sangel.springcloud.msvc.cursos.msvccursos.service.impl;

import org.modelmapper.ModelMapper;
import org.sangel.springcloud.msvc.cursos.msvccursos.DTO.CursoDTO;
import org.sangel.springcloud.msvc.cursos.msvccursos.DTO.Usuario;
import org.sangel.springcloud.msvc.cursos.msvccursos.clients.UsuarioClientRest;
import org.sangel.springcloud.msvc.cursos.msvccursos.mensajes.ExepcionNoEncontrado;
import org.sangel.springcloud.msvc.cursos.msvccursos.model.Curso;
import org.sangel.springcloud.msvc.cursos.msvccursos.model.CursoUsuario;
import org.sangel.springcloud.msvc.cursos.msvccursos.respository.CursoRepository;
import org.sangel.springcloud.msvc.cursos.msvccursos.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    private UsuarioClientRest usuarioClientRest;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return cursoRepository.findAll();
    }

    @Override
    public Optional<Curso> porId(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    @Transactional
    public CursoDTO actualizar(CursoDTO cursoDTO, Long id) {
        Optional<Curso> curso = porId(id);
        if(curso.isEmpty()){
            throw new ExepcionNoEncontrado("El usuario no ha sido encontrado");
        }else{
            Curso cursoToUpdate = mapper.map(cursoDTO,Curso.class);
            cursoToUpdate.setId(id);
            cursoRepository.save(cursoToUpdate);
        }
        return mapper.map(porId(id),CursoDTO.class);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        cursoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void eliminarCursoUsuario(Long id) {
        cursoRepository.deletCursoUsuarioById(id);
    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {

        Optional<Curso> o = cursoRepository.findById(cursoId);
        if(o.isPresent()){
            Usuario usuarioMsvc = usuarioClientRest.porId(usuario.getId());
            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());
            curso.addCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return Optional.of(usuarioMsvc);
        }else{
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = cursoRepository.findById(cursoId);
        if(o.isPresent()){
            Usuario usuarioNuevoMsvc = usuarioClientRest.registrarUsuario(usuario);
            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioNuevoMsvc.getId());
            curso.addCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return Optional.of(usuarioNuevoMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = cursoRepository.findById(cursoId);
        if(o.isPresent()){
            Usuario usuarioMsvc = usuarioClientRest.porId(usuario.getId());
            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());
            curso.removeCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return Optional.of(usuarioMsvc);
        }


        return Optional.empty();
    }

    @Override
    public Optional<Curso> porIdConUsuarios(Long id) {
        Optional<Curso> o = cursoRepository.findById(id);

        if(o.isPresent()){
            Curso curso = o.get();
            if(!curso.getCursoUsuarios().isEmpty()){
                List<Long> ids = curso.getCursoUsuarios().stream()
                        .map(cu -> cu.getUsuarioId()).toList();

                List<Usuario> usuarios = usuarioClientRest.obtenerAlumnosPorCurso(ids);
                curso.setUsuarios(usuarios);
            }
            return Optional.of(curso);
        }

        return Optional.empty();
    }
}
