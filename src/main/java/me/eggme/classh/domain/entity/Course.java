package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import me.eggme.classh.domain.dto.*;
import me.eggme.classh.utils.ModelMapperUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"signUpCourses", "instructor", "courseSections", "tags", "recommendations", "courseReviews"})
@ToString(exclude = {"signUpCourses", "instructor", "courseSections", "tags", "recommendations", "courseReviews"})
public class Course extends BaseTimeEntity implements Serializable {

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
    @JsonManagedReference
    @OneToMany(mappedBy = "course")
    private List<SignUpCourse> signUpCourses = new ArrayList<>();

    // 이 강의의 강사
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    private Instructor instructor;

    // 이 강의가 가지고 있는 섹션
    @JsonManagedReference
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id asc")
    private List<CourseSection> courseSections = new ArrayList<>();

    // 강의의 태그를 동적으로 늘릴 수 있음
    @JsonManagedReference
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SkillTag> skillTags = new ArrayList<>();

    // 강의의 추천하는 사람의 정보
    @JsonManagedReference
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
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
    @JsonManagedReference
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<CourseReview> courseReviews = new ArrayList<>();

    // 강의 공지사항
    @JsonManagedReference
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<CourseNotice> courseNotices = new ArrayList<>();

    // 강의 자체 관련 태그 1:N 단방향
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="COURSE_TAG_ID")
    private List<CourseTag> courseTags = new ArrayList<>();

    public Course(String name){
        this.name = name;
        this.courseState = CourseState.TEMPORARY;
    }

    public CourseDTO of(){
        return ModelMapperUtils.getModelMapper().map(this, CourseDTO.class);
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

    // 총 강의 시간
    public int getTotalTime(){
        int totalTime = courseSections.stream().mapToInt(s ->
            s.getCourseClasses().stream().mapToInt(c ->
                    c.getSeconds())
                    .sum())
                .sum();
        return totalTime;
    }


    // 총 강의 수
    public int getTotalClassCount(){
        int totalClassCount = courseSections.stream().mapToInt(s -> s.getCourseClasses().size()).sum();
        return totalClassCount;
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

    // 편의 메서드 리뷰 등록
    public void addCourseReview(CourseReview courseReview){
        this.courseReviews.add(courseReview);
        courseReview.setCourse(this);
    }

    // 편의 메서드 태그 등록
    public void addSkillTag(SkillTag skillTag){
        this.skillTags.add(skillTag);
        skillTag.setCourse(this);
    }
}
