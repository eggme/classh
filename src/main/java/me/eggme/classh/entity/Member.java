package me.eggme.classh.entity;

import lombok.*;
import me.eggme.classh.security.UserRole;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseTimeEntity{
    // 멤버 PK
    @Setter
    @Id @GeneratedValue
    private Long id;

    // 사용자 아이디
    @Setter
    @Column(length = 50, nullable = false)
    private String email;

    // 사용자 이름
    @Setter
    @Column(length = 20, nullable = false)
    private String name;

    // 사용자 자기소개
    @Setter
    private String selfIntroduce;

    // 사용자 비밀번호
    @Setter
    @Column(length = 512, nullable = false)
    private String password;

    // 스프링 시큐리티 인증관련
    @Setter
    @Column(nullable = false)
    private boolean isEnable = true;

    // 스프링 시큐리티 인증관련
    @Setter
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    // 사용자 프로필 사진
    @Setter
    @Column(nullable = false)
    private String profile = "/imgs/mini_icon_1.png";

    // 내가 듣고 있는 강의들
    @Setter
    @ManyToOne
    private SignUpCourse signUpCourse;

    // 내가 수업하고 있는 강의들
    @Setter
    @ManyToOne
    private Instructor instructor;
//    @OneToMany(mappedBy = "instructor")
//    private List<Course> instructorCourseList;

    // 내가 올린 강의 리뷰
    @OneToMany(mappedBy = "member")
    private List<CourseReview> courseReviews = new ArrayList<>();

    @Builder
    public Member(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
