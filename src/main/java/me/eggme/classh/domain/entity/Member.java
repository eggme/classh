package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import me.eggme.classh.domain.dto.MemberDTO;
import me.eggme.classh.domain.dto.MemberHistoryDTO;
import me.eggme.classh.utils.ModelMapperUtils;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"signUpCourses", "instructor", "courseReviews", "memberRoles", "courseQuestions", "cart", "payments", "courseHistories", "notifications"})
@ToString(exclude = {"signUpCourses", "instructor", "courseReviews", "memberRoles", "courseQuestions", "cart", "payments", "courseHistories", "notifications"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Member extends BaseTimeEntity implements Serializable {
    // 멤버 PK
    @Id
    @GeneratedValue
    private Long id;

    // 사용자 아이디
    @Column(length = 50, nullable = false)
    private String username;

    // 사용자 이름
    @Column(length = 20, nullable = false)
    private String nickName;

    // 사용자의 실명
    private String memberName;

    // 사용자의 이메일
    private String email;

    // 사용자의 연락처
    private String tel;

    // 사용자 자기소개
    private String selfIntroduce;

    // 사용자 비밀번호
    @Column(length = 512, nullable = false)
    private String password;

    // 스프링 시큐리티 인증관련, 해당 유저의 권한들
    @JsonManagedReference
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<MemberRoles> memberRoles = new ArrayList<>();

    // 사용자 프로필 사진
    @Column(nullable = false)
    private String profile = "/imgs/mini_icon_1.png";

    // 내가 듣고 있는 강의들
    @JsonManagedReference
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<SignUpCourse> signUpCourses = new ArrayList<>();

    // 내가 수업하고 있는 강의들
    @JsonBackReference
    @OneToOne(mappedBy = "member", orphanRemoval = true)
    private Instructor instructor;

    // 내가 올린 강의 리뷰
    @JsonManagedReference
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("create_at asc")
    @BatchSize(size = 10)
    private List<CourseReview> courseReviews = new ArrayList<>();

    // 내가 올린 질문
    @JsonManagedReference
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("create_at asc")
    @BatchSize(size = 10)
    private List<CourseQuestion> courseQuestions = new ArrayList<>();

    @JsonBackReference
    @OneToOne(mappedBy = "member")
    @OrderBy("create_at asc")
    @BatchSize(size = 10)
    private Cart cart;

    @JsonManagedReference
    @OneToMany(mappedBy = "member")
    @OrderBy("create_at asc")
    @BatchSize(size = 10)
    private List<Payment> payments = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<CourseHistory> courseHistories = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "member", orphanRemoval = true, fetch = FetchType.EAGER)
    @BatchSize(size = 10)
    private List<Notification> notifications = new ArrayList<>();

    @Builder
    public Member(String username, String password, String nickName) {
        this.username = username;
        this.password = password;
        this.nickName = nickName;
    }

    public MemberDTO of() {
        return ModelMapperUtils.getModelMapper().map(this, MemberDTO.class);
    }

    public MemberHistoryDTO ofHistory() {
        MemberHistoryDTO memberHistoryDTO = new MemberHistoryDTO();
        memberHistoryDTO.setCourseHistories(this.getCourseHistories());
        return memberHistoryDTO;
    }

    public void addNotification(Notification notification) {
        this.getNotifications().add(notification);
    }

    public void addPayment(Payment payment) {
        this.getPayments().add(payment);
    }

    public void addCourseHistory(CourseHistory courseHistory) {
        this.getCourseHistories().add(courseHistory);
    }

    public boolean isPutInTheCart(Long course_id) {
        if (this.getCart() != null) {
            Course course = this.getCart().getCourses().stream().filter(c ->
                    c.getId() == course_id).findFirst().orElse(null);
            if (course != null) return true;
        }
        return false;
    }

    public boolean isNotification(){
        for(Notification notification : notifications){
            if(!notification.isRead()) return true;
        }
        return false;
    }


    // 연관관계 편의 메소드 - 수강 신청
    public void connectCourse(Course course, SignUpCourse signUpCourse) {
        signUpCourse.setMember(this);
        signUpCourse.setCourse(course);
        this.signUpCourses.add(signUpCourse);
        course.getSignUpCourses().add(signUpCourse);

    }

    // 편의 메서드 해당 강의에 수강평을 등록했는지 여부
    public boolean isRegisteredReview(Course course) {
        boolean isRegistered = courseReviews.stream().anyMatch(c -> c.getCourse().getId() == course.getId());
        return isRegistered;
    }

    public void addSignUpCourse(SignUpCourse signUpCourse) {
        this.getSignUpCourses().add(signUpCourse);
        signUpCourse.setMember(this);
    }

    public void addCourseReview(CourseReview courseReview) {
        this.courseReviews.add(courseReview);
        courseReview.setMember(this);
    }
}
