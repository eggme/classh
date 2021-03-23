package me.eggme.classh.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseClassDTO {
    private Long id;
    private String name;
    private String mediaPath;
    private boolean isPublic;
    private String dataPath;
    private int seconds;
    private String instructorMemo;
}
