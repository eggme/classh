package me.eggme.classh.exception;

public class NoSearchCourseSectionException extends RuntimeException{

    public NoSearchCourseSectionException(){
        super("해당되는 섹션이 없습니다.");
    }

}
