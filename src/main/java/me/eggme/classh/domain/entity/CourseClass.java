package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import me.eggme.classh.domain.dto.CourseClassDTO;
import me.eggme.classh.utils.ModelMapperUtils;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "courseSection")
@EqualsAndHashCode(exclude = "courseSection")
public class CourseClass implements Serializable {

    @Id @GeneratedValue
    private Long id;

    // 수업 제목
    private String name;

    // 수업 영상 경로
    private String mediaPath;

    // 무료 영상 공개
    private boolean preview = false;

    // 자료 파일 경로
    private String dataPath;

    // 강의 시간
    private int seconds;

    // 강사가 남기는 메모
    private String instructorMemo;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private CourseSection courseSection;

    public CourseClassDTO of(){
        return ModelMapperUtils.getModelMapper().map(this, CourseClassDTO.class);
    }
}
