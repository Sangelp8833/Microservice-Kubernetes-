package org.sangel.springcloud.msvc.usuarios.msvcusuarios.controllers;

import org.modelmapper.ModelMapper;
import org.sangel.springcloud.msvc.usuarios.msvcusuarios.DTO.UsuarioDTO;
import org.sangel.springcloud.msvc.usuarios.msvcusuarios.Service.UsuarioService;
import org.sangel.springcloud.msvc.usuarios.msvcusuarios.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/list")
    public ResponseEntity<?> listar(){
        return new ResponseEntity(usuarioService.listar(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> porId(@PathVariable(name = "id") Long id){
        if(usuarioService.porId(id).isPresent()){
            return new ResponseEntity<>(modelMapper.map(usuarioService.porId(id).get(),UsuarioDTO.class), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("El usuario no ha sido encontrado" ,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody Usuario usuario){
        return new ResponseEntity<>(usuarioService.guardar(usuario),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable(name = "id") Long id,@Valid @RequestBody UsuarioDTO usuario){
        return new ResponseEntity<>(usuarioService.actualizar(usuario,id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable(name = "id") Long id){
        if(usuarioService.porId(id).isPresent()){
            usuarioService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
