package me.eggme.classh.entity;

import lombok.*;
import me.eggme.classh.dto.CourseCategory;
import me.eggme.classh.dto.CourseLevel;
import me.eggme.classh.dto.CourseState;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"signUpCourses", "instructor", "courseSections", "tags", "recommendations", "courseReviews"})
@ToString(exclude = {"signUpCourses", "instructor", "courseSections", "tags", "recommendations", "courseReviews"})
public class Course extends BaseTimeEntity{

    @Id @GeneratedValue
    private Long id;

    // 강의 이름
    @Column(nullable = false)
    private String name;

    // 강의 가격
    private int price;

    @Column(unique = true)
    private String url;

    // 강의의 상태
    @Enumerated(value = EnumType.STRING)
    private CourseState courseState;

    // 이 강의를 수강하는 학생
    @OneToMany(mappedBy = "course")
    private List<SignUpCourse> signUpCourses = new ArrayList<>();

    // 이 강의의 강사
    @ManyToOne(fetch = FetchType.EAGER)
    private Instructor instructor;

    // 이 강의가 가지고 있는 섹션
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<CourseSection> courseSections = new ArrayList<>();

    // 강의의 태그를 동적으로 늘릴 수 있음
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();

    // 강의의 추천하는 사람의 정보
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Recommendation> recommendations = new ArrayList<>();

    // 강의의 레벨
    @Enumerated(value = EnumType.STRING)
    private CourseLevel courseLevel;

    // 강의 카테고리
    @Enumerated(value = EnumType.STRING)
    private CourseCategory courseCategory;

    // 강의의 짧은설명
    private String shortDesc;
    // 강의의 긴 설명
    private String longDesc;

    // 강의의 등록 이미지 경로
    @Column(nullable = false)
    private String courseImg = "/imgs/default_course_image.png";

    // 강의 수강평
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<CourseReview> courseReviews = new ArrayList<>();

    public Course(String name){
        this.name = name;
        this.courseState = CourseState.TEMPORARY;
    }

    // 편의 메서드
    // 총 수강생 수
    public int totalStudents(){
        return this.signUpCourses.size();
    }

    // 총 질문 수
    public int totalQuestions(){
        return this.courseReviews.size();
    }

    // 총 수입
    public int totalPrice(){
        return (this.signUpCourses.size() * this.getPrice());
    }

    // 편의 메서드
    // 강의 섹션 등록
    public void addCourseSession(CourseSection courseSection){
        courseSections.add(courseSection);
        courseSection.setCourse(this);
    }

    // 편의 메서드 추천하는사람 등록
    public void addCourseRecommendation(Recommendation recommendation){
        this.recommendations.add(recommendation);
        recommendation.setCourse(this);
    }

    // 편의 메서드 태그 등록
    public void addCourseTag(Tag tag){
        this.tags.add(tag);
        tag.setCourse(this);
    }
}
