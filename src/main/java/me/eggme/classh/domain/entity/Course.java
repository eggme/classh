package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import me.eggme.classh.domain.dto.*;
import me.eggme.classh.utils.CourseValidation;
import me.eggme.classh.utils.ModelMapperUtils;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"signUpCourses", "instructor", "courseSections", "skillTags", "recommendations", "courseReviews", "courseNotices", "courseTags", "courseQuestions"})
@ToString(exclude = {"signUpCourses", "instructor", "courseSections", "skillTags", "recommendations", "courseReviews", "courseNotices", "courseTags", "courseQuestions"})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Course extends BaseTimeEntity implements Serializable {

    @Id @GeneratedValue
    private Long id;

    // 강의 이름
    @Column(nullable = false)
    private String name;

    // 강의 가격
    private int price;

    // 강의의 상태
    @Enumerated(value = EnumType.STRING)
    private CourseState courseState;

    // 이 강의를 수강하는 학생
    @JsonManagedReference
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 10)
    private Set<SignUpCourse> signUpCourses = new HashSet<>();

    // 이 강의의 강사
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    private Instructor instructor;

    // 이 강의가 가지고 있는 섹션
    @JsonManagedReference
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id asc")
    private Set<CourseSection> courseSections = new LinkedHashSet<>();

    // 강의의 태그를 동적으로 늘릴 수 있음
    @JsonManagedReference
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 10)
    private Set<SkillTag> skillTags = new LinkedHashSet<>();

    // 강의의 추천하는 사람의 정보
    @JsonManagedReference
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 10)
    private Set<Recommendation> recommendations = new LinkedHashSet<>();

    // 강의의 레벨
    @Enumerated(value = EnumType.STRING)
    private CourseLevel courseLevel;

    // 강의 카테고리
    @Enumerated(value = EnumType.STRING)
    private CourseCategory courseCategory;

    // 강의의 짧은설명
    @Column(length = 500)
    private String shortDesc;
    // 강의의 긴 설명
    @Column(columnDefinition = "CLOB")
    private String longDesc;

    // 강의의 등록 이미지 경로
    @Column(nullable = false)
    private String courseImg = "/imgs/default_course_image.png";

    // 강의 수강평
    @JsonManagedReference
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 10)
    private Set<CourseReview> courseReviews = new HashSet<>();

    // 강의 공지사항
    @JsonManagedReference
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 10)
    private Set<CourseNotice> courseNotices = new HashSet<>();

    // 강의 자체 관련 태그 1:N 단방향
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="COURSE_TAG_ID")
    private Set<CourseTag> courseTags = new HashSet<>();
    // 강의 질문
    @JsonManagedReference
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("modify_at desc")
    @BatchSize(size = 10)
    private Set<CourseQuestion> courseQuestions = new HashSet<>();

    // 강의 검증관련 컬럼에 매핑되지 않음
    @Transient
    private CourseValidation courseValidation;

    public Course(String name){
        this.name = name;
        this.courseState = CourseState.TEMPORARY;
    }

    public Course(Long id, String name, Instructor instructor, String courseImg, int price, CourseCategory courseCategory, CourseState courseState){
        this.id = id;
        this.name = name;
        this.courseImg = courseImg;
        this.instructor = instructor;
        this.price = price;
        this.courseCategory = courseCategory;
        this.courseState = courseState;
    }

    @Override
    public boolean equals(Object o){
        Course courseObject = (Course)o;
        if(this.getId() == courseObject.getId()){
            return true;
        }else{
            return false;
        }
    }

    public CourseDTO of(){
        CourseDTO courseDTO = ModelMapperUtils.getModelMapper().map(this, CourseDTO.class);
        String myCourseInstructorNickName = this.getInstructor().getCourses().stream().filter(c ->
                c.getId() == this.getId()).findFirst().map(fc ->
                fc.getInstructor().getMember().getNickName()).orElse(new String());
        courseDTO.setNickName(myCourseInstructorNickName);
        courseDTO.setCourseValidation(new CourseValidation(courseDTO));
        return courseDTO;
    }

    public CourseMappingDTO mapping(){
        CourseMappingDTO courseMappingDTO = ModelMapperUtils.getModelMapper().map(this, CourseMappingDTO.class);
        return courseMappingDTO;
    }

    public void deleteCourse(){
        /* 수강 관계 제거 */
        if(this.getSignUpCourses() != null) this.getSignUpCourses().stream().forEach(sc-> sc.deleteSignUpCourse());
        if(this.getCourseSections() != null) this.getCourseSections().stream().forEach(ss -> ss.deleteCourseSection());
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

    public void addSignUpCourse(SignUpCourse signUpCourse){
        this.signUpCourses.add(signUpCourse);
        signUpCourse.setCourse(this);
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
