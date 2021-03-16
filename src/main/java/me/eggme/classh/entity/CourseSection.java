package me.eggme.classh.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class CourseSection {

    @Id @GeneratedValue
    private Long id;

    // 섹션 제목
    private String name;

    // 학습 목표
    private String goal;

    // 섹션이 가지고 있는 수업들
    @OneToMany(mappedBy = "courseSection")
    private List<CourseClass> courseClasses = new ArrayList<>();

    // 어느 강의에 포함되는지 정함
    @ManyToOne
    private Course course;
}
