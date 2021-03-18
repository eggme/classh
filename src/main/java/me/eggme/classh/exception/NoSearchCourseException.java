package me.eggme.classh.exception;

public class NoSearchCourseException extends RuntimeException{

    public NoSearchCourseException() {
        super("해당되는 강의가 없습니다.");
    }
}
