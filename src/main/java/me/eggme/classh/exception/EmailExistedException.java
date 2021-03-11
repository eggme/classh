package me.eggme.classh.exception;

import org.springframework.security.core.AuthenticationException;

public class EmailExistedException extends AuthenticationException {

    public EmailExistedException(String msg){
        super("Input Email -> "+ msg +" , NotFoundException");
    }
}
