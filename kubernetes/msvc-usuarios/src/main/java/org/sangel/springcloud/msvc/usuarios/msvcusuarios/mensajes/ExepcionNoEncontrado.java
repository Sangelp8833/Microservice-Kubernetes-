package org.sangel.springcloud.msvc.usuarios.msvcusuarios.mensajes;

public class ExepcionNoEncontrado extends RuntimeException{

    public ExepcionNoEncontrado(String errorDetalle){
        super(errorDetalle);
    }
}
