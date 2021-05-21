package me.eggme.classh.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CourseState {
    TEMPORARY("임시저장"),
    SUBMIT("제출"),
    REJECT("승인거절"),
    RELEASE("승인완료");

    private String value;

    public static CourseState getState(String state){
        for(CourseState courseState : CourseState.values()){
            if(courseState.getValue().equals(state)){
                return courseState;
            }
        }
        return null;
    }

}
