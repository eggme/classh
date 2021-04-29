package me.eggme.classh.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.eggme.classh.domain.entity.Course;
import me.eggme.classh.domain.entity.CourseReview;
import me.eggme.classh.domain.entity.Instructor;
import me.eggme.classh.domain.entity.SignUpCourse;
import me.eggme.classh.domain.entity.Role;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO implements Serializable {

    private Long id;
    private String name;
    private String email;
    private String profile;
    private String selfIntroduce;
    private boolean isEnable;
    private Role role;
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
