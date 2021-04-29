package me.eggme.classh.domain.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseClassDTO implements Serializable {
    private Long id;
    private String name;
    private String mediaPath;
    private boolean status;
    private String dataPath;
    private int seconds;
    private String instructorMemo;
}
