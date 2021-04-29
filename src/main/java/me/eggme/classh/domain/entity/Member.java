package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import me.eggme.classh.domain.dto.MemberDTO;
import me.eggme.classh.utils.ModelMapperUtils;

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
@EqualsAndHashCode(exclude = {"signUpCourses", "instructor", "courseReviews"})
@ToString(exclude = {"signUpCourses", "instructor", "courseReviews"})
public class Member extends BaseTimeEntity implements Serializable {
    // 멤버 PK
    @Id @GeneratedValue
    private Long id;

    // 사용자 아이디
    @Column(length = 50, nullable = false)
    private String email;

    // 사용자 이름
    @Column(length = 20, nullable = false)
    private String name;

    // 사용자 자기소개
    private String selfIntroduce;

    // 사용자 비밀번호
    @Column(length = 512, nullable = false)
    private String password;

    // 스프링 시큐리티 인증관련, 해당 유저의 권한들
    @JsonBackReference
    @OneToOne(mappedBy = "member")
    private MemberRoles memberRoles;

    // 사용자 프로필 사진
    @Column(nullable = false)
    private String profile = "/imgs/mini_icon_1.png";

    // 내가 듣고 있는 강의들
    @JsonManagedReference
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<SignUpCourse> signUpCourses = new ArrayList<>();

    // 내가 수업하고 있는 강의들
    @JsonBackReference
    @OneToOne(mappedBy = "member")
    private Instructor instructor;

    // 내가 올린 강의 리뷰
    @JsonManagedReference
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<CourseReview> courseReviews = new ArrayList<>();

    @Builder
    public Member(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public MemberDTO of(){
        return ModelMapperUtils.getModelMapper().map(this, MemberDTO.class);
    }

    // 연관관계 편의 메소드 - 수강 신청
    public void connectCourse(Course course, SignUpCourse signUpCourse){
        this.signUpCourses.add(signUpCourse);
        signUpCourse.setMember(this);
        course.getSignUpCourses().add(signUpCourse);
        signUpCourse.setCourse(course);
    }

    // 편의 메서드 해당 강의에 수강평을 등록했는지 여부
    public boolean isRegisteredReview(Course course){
        boolean isRegistered = courseReviews.stream().anyMatch(c -> c.getCourse().getId() == course.getId());
        return isRegistered;
    }

    public void addCourseReview(CourseReview courseReview){
        this.courseReviews.add(courseReview);
        courseReview.setMember(this);
    }

    public void addRole(Role role){
        this.memberRoles.addRole(role);
        this.memberRoles.setMember(this);
    }
}
