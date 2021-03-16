package me.eggme.classh.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CourseLevel {

    BEGINNING("초급"),
    INTERMEDIATE("중급"),
    HIGH("고급");

    private String value;
}
