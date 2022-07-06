package org.sangel.springcloud.msvc.usuarios.msvcusuarios.mensajes;

public class ExepcionYaExiste extends RuntimeException{

    public ExepcionYaExiste(String mensaje){
        super(mensaje);
    }
}
