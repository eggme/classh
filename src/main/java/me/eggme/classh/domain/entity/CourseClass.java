package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import me.eggme.classh.domain.dto.CourseClassDTO;
import me.eggme.classh.utils.ModelMapperUtils;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "courseSection")
@EqualsAndHashCode(exclude = "courseSection")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
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
    @ManyToOne
    private CourseSection courseSection;

    @JsonManagedReference
    @OneToMany(mappedBy = "courseClass", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 10)
    private Set<CourseQuestion> courseQuestions = new HashSet<>();

    public CourseClassDTO of(){
        return ModelMapperUtils.getModelMapper().map(this, CourseClassDTO.class);
    }

    /* 연관관계 편의 메서드 */
    public void deleteCourseClass(){
        this.setCourseSection(null);
        if(this.getCourseQuestions() != null) this.getCourseQuestions().stream().forEach(cq -> cq.deleteCourseQuestion());
    }
}
