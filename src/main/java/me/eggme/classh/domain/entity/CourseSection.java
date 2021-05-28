package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import me.eggme.classh.domain.dto.CourseSectionDTO;
import me.eggme.classh.utils.ModelMapperUtils;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"courseClasses", "course"})
@EqualsAndHashCode(exclude = {"courseClasses", "course"})
public class CourseSection implements Serializable {

    @Id @GeneratedValue
    private Long id;

    // 섹션 제목
    private String name;

    // 학습 목표
    private String goal;

    // 섹션이 가지고 있는 수업들
    @JsonManagedReference
    @OneToMany(mappedBy = "courseSection", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("id asc")
    @BatchSize(size = 10)
    private Set<CourseClass> courseClasses = new LinkedHashSet<>();

    // 어느 강의에 포함되는지 정함
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;

    public int getTotalTime(){
        int totalTime = courseClasses.stream().mapToInt(c -> c.getSeconds()).sum();
        return totalTime;
    }

    public void addCourseClass(CourseClass courseClass){
        this.courseClasses.add(courseClass);
        courseClass.setCourseSection(this);
    }

    public CourseSectionDTO of(){
        return ModelMapperUtils.getModelMapper().map(this, CourseSectionDTO.class);
    }

    /* 연관관계 편의 메서드 */
    public void deleteCourseSection(){
        this.setCourse(null);
        if(this.getCourseClasses() != null )this.getCourseClasses().stream().forEach(cc -> cc.deleteCourseClass());
    }
}
