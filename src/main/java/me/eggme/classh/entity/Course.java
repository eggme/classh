package me.eggme.classh.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.eggme.classh.dto.CourseLevel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Course extends BaseTimeEntity{

    @Id @GeneratedValue
    private Long id;

    // 강의 이름
    @Column(nullable = false)
    private String name;

    // 강의 가격
    private int price;

    // 이 강의를 수강하는 학생
    @ManyToOne
    private SignUpCourse signUpCourse;

    // 이 강의의 강사
//    @ManyToOne
//    private Member instructor;
    @Setter
    @ManyToOne
    private Instructor instructor;

    // 이 강의가 가지고 있는 섹션
    @OneToMany(mappedBy = "course")
    private List<CourseSection> courseSections = new ArrayList<>();

    // 강의의 태그를 동적으로 늘릴 수 있음
    @OneToMany(mappedBy = "course")
    private List<Tag> tags = new ArrayList<>();

    // 강의의 추천하는 사람의 정보
    @OneToMany(mappedBy = "course")
    private List<Recommendation> recommendations = new ArrayList<>();

    // 강의의 레벨
    @Enumerated(value = EnumType.STRING)
    private CourseLevel courseLevel;
    // 강의 카테고리

    // 강의의 짧은설명
    private String shortDesc;
    // 강의의 긴 설명
    private String longDesc;

    // 강의의 등록 이미지 경로
    private String courseImg;

    // 강의 수강평
    @OneToMany(mappedBy = "course")
    private List<CourseReview> courseReviews = new ArrayList<>();

    public Course(String name){
        this.name = name;
    }
}
