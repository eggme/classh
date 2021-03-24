package me.eggme.classh.exception;

public class NoSearchCourseClassException extends RuntimeException{

    public NoSearchCourseClassException(){
        super("해당되는 강의가 없습니다");
    }
}
