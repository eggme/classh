package me.eggme.classh.exception;

import org.springframework.security.core.AuthenticationException;

public class PasswordWrongException extends AuthenticationException {

    public PasswordWrongException(){
        super("비밀번호가 틀렸습니다.");
    }
}
