package me.eggme.classh.dto;

import lombok.Data;
import me.eggme.classh.entity.Course;
import me.eggme.classh.entity.CourseReview;
import me.eggme.classh.entity.Instructor;
import me.eggme.classh.entity.SignUpCourse;
import me.eggme.classh.security.UserRole;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MemberDTO implements Serializable {

    private Long id;
    private String name;
    private String email;
    private String profile;
    private String selfIntroduce;
    private boolean isEnable;
    private UserRole userRole;
    private List<SignUpCourse> signUpCourses;
    private Instructor instructor;
    private List<CourseReview> courseReviews;
    private LocalDateTime create_at = LocalDateTime.now();
    private LocalDateTime modify_at = LocalDateTime.now();

    // 편의 메서드 해당 강의에 수강평을 등록했는지 여부
    public boolean isRegisteredReview(Course course){
        boolean isRegistered = courseReviews.stream().anyMatch(c -> c.getCourse().getId() == course.getId());
        return isRegistered;
    }
}
