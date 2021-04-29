package me.eggme.classh.domain.dto;

import lombok.Getter;

public enum CourseLevel {

    BEGINNING("초급"),
    INTERMEDIATE("중급"),
    HIGH("고급");

    @Getter
    private String value;

    CourseLevel(String value){
        this.value = value;
    }

    public static CourseLevel findLevel(String level){
        for(CourseLevel c : CourseLevel.values()){
            if(c.getValue().equals(level)){
                return c;
            }
        }
        return null;
    }
}
