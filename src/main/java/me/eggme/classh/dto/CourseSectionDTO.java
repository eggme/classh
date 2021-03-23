package me.eggme.classh.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CourseSectionDTO {
    private Long id;
    private String name;
    private String goal;
    private List<CourseClassDTO> classDTOList;
}
