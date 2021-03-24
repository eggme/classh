package me.eggme.classh.entity;

import lombok.*;
import me.eggme.classh.dto.CourseSectionDTO;
import me.eggme.classh.utils.ModelMapperUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"courseClasses", "course"})
@EqualsAndHashCode(exclude = {"courseClasses", "course"})
public class CourseSection {

    @Id @GeneratedValue
    private Long id;

    // 섹션 제목
    private String name;

    // 학습 목표
    private String goal;

    // 섹션이 가지고 있는 수업들
    @OneToMany(mappedBy = "courseSection", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CourseClass> courseClasses = new ArrayList<>();

    // 어느 강의에 포함되는지 정함
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
}
