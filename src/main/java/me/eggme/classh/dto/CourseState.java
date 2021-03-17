package me.eggme.classh.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CourseState {
    TEMPORARY("임시저장"),
    EXAMINE("검수중"),
    REJECT("승인거절"),
    RELEASE("승인완료");


    private String value;
}
