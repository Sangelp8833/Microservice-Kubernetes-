package org.sangel.springcloud.msvc.cursos.msvccursos.controllers;

import org.modelmapper.ModelMapper;
import org.sangel.springcloud.msvc.cursos.msvccursos.DTO.CursoDTO;
import org.sangel.springcloud.msvc.cursos.msvccursos.model.Curso;
import org.sangel.springcloud.msvc.cursos.msvccursos.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        if(cursoService.porId(id).isPresent()){
            return new ResponseEntity<>(modelMapper.map(cursoService.porId(id).get(),CursoDTO.class), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> registrarUsuario(@RequestBody Curso curso){
        return new ResponseEntity<>(cursoService.guardar(curso),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody CursoDTO cursoDTO){
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

}
