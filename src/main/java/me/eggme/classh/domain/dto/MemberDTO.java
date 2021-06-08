package me.eggme.classh.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.eggme.classh.domain.entity.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO implements Serializable {

    private Long id;
    private String nickName;
    private String username;
    private String password;
    private String profile;
    private String memberName;
    private String email;
    private String tel;
    private String selfIntroduce;
    private MemberRoles memberRoles;
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
