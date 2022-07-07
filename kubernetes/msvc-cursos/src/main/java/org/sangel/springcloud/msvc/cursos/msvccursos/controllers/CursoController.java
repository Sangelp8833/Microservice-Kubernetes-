package org.sangel.springcloud.msvc.cursos.msvccursos.controllers;

import feign.FeignException;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.modelmapper.ModelMapper;
import org.sangel.springcloud.msvc.cursos.msvccursos.DTO.CursoDTO;
import org.sangel.springcloud.msvc.cursos.msvccursos.DTO.Usuario;
import org.sangel.springcloud.msvc.cursos.msvccursos.model.Curso;
import org.sangel.springcloud.msvc.cursos.msvccursos.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/curso")
public class CursoController {

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private CursoService cursoService;

    @GetMapping("/list")
    public ResponseEntity<?> listar(){
        return new ResponseEntity(cursoService.listar(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> porId(@PathVariable Long id){
        if(cursoService.porIdConUsuarios(id).isPresent()){      //porId(id).isPresent()){
            return new ResponseEntity<>(cursoService.porId(id).get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody Curso curso){
        return new ResponseEntity<>(cursoService.guardar(curso),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id,@Valid @RequestBody CursoDTO cursoDTO){
        return new ResponseEntity<>(cursoService.actualizar(cursoDTO,id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id){
        if(cursoService.porId(id).isPresent()){
            cursoService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable(name = "cursoId") Long cursoId){
        Optional<Usuario> o = null;
        try{
            o = cursoService.asignarUsuario(usuario,cursoId);
        }catch (FeignException e){
            return new ResponseEntity<>(Collections.singletonMap("Mensaje", "No existe el usuario por el id ingresado " + e.getMessage())
                    ,HttpStatus.NOT_FOUND);
        }
        if(o.isPresent()){
            return new ResponseEntity<>(o.get(),HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> o = null;
        try{
            o = cursoService.crearUsuario(usuario,cursoId);
        }catch (FeignException e){
            return new ResponseEntity<>(Collections.singletonMap("Mensaje", "No fue posible crear el usuario " + e.getMessage())
                    ,HttpStatus.NOT_FOUND);
        }
        if(o.isPresent()){
            return new ResponseEntity<>(o.get(),HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> o = null;
        try{
            o = cursoService.eliminarUsuario(usuario,cursoId);
        }catch (FeignException e){
            return new ResponseEntity<>(Collections.singletonMap("Mensaje", "No existe el usuario por el id ingresado " + e.getMessage())
                    ,HttpStatus.NOT_FOUND);
        }
        if(o.isPresent()){
            return new ResponseEntity<>(o.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/borrar-usuario/{usuarioId}")
    public ResponseEntity<?> eliminarCursoUsuario(@PathVariable Long usuarioId){
        cursoService.eliminarCursoUsuario(usuarioId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
