package me.eggme.classh.exception;

public class EmailExistedException extends RuntimeException{

    public EmailExistedException(String msg){
        super(msg);
    }
}
