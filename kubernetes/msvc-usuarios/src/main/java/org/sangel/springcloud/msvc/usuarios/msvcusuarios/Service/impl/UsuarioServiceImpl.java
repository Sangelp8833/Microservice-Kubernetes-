package org.sangel.springcloud.msvc.usuarios.msvcusuarios.Service.impl;

import org.modelmapper.ModelMapper;
import org.sangel.springcloud.msvc.usuarios.msvcusuarios.DTO.UsuarioDTO;
import org.sangel.springcloud.msvc.usuarios.msvcusuarios.Service.UsuarioService;
import org.sangel.springcloud.msvc.usuarios.msvcusuarios.client.CursoClienteRest;
import org.sangel.springcloud.msvc.usuarios.msvcusuarios.mensajes.ExepcionNoEncontrado;
import org.sangel.springcloud.msvc.usuarios.msvcusuarios.mensajes.ExepcionYaExiste;
import org.sangel.springcloud.msvc.usuarios.msvcusuarios.model.Usuario;
import org.sangel.springcloud.msvc.usuarios.msvcusuarios.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoClienteRest clienteRest;

    private final ModelMapper mapper = new ModelMapper();

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> porId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) throws ExepcionYaExiste {
        if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent()){
            throw new ExepcionYaExiste("El usuario  ya existe");
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public UsuarioDTO actualizar(UsuarioDTO usuarioDTO, Long id) throws ExepcionNoEncontrado {
        Optional<Usuario> user = porId(id);
        if(user.isEmpty()){
            throw new ExepcionNoEncontrado("El usuario no ha sido encontrado");
        }else{
            Usuario userToUpdate = mapper.map(usuarioDTO,Usuario.class);
            userToUpdate.setId(id);
            usuarioRepository.save(userToUpdate);
        }
        return mapper.map(porId(id),UsuarioDTO.class);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
        clienteRest.eliminarCursoUsuario(id);
    }

    @Override
    public List<Usuario> findById(Iterable<Long> id) {
        return usuarioRepository.findAllById(id);
    }
}
