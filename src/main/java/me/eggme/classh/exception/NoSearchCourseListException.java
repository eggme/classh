package me.eggme.classh.exception;

public class NoSearchCourseListException extends RuntimeException{
    public NoSearchCourseListException(){
        super("해당 유저의 강의가 없습니다");
    }
}
