package org.sangel.springcloud.msvc.cursos.msvccursos.mensajes;

public class ExepcionNoEncontrado extends RuntimeException{

    public ExepcionNoEncontrado(String errorDetalle){
        super(errorDetalle);
    }
}
