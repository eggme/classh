package me.eggme.classh.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CourseSectionDTO {
    private Long id;
    private String name;
    private String goal;
    private List<CourseClassDTO> classDTOList;
}
