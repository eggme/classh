package me.eggme.classh.domain.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseSectionDTO implements Serializable {
    private Long id;
    private String name;
    private String goal;
    private List<CourseClassDTO> classDTOList;
}
