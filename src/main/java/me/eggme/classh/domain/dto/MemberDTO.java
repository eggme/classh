package me.eggme.classh.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.eggme.classh.domain.entity.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO implements Serializable {

    private Long id;
    private String username;
    private String nickName;
    private String memberName;
    private String email;

    @JsonIgnore
    private String password;

    private String tel;
    private String selfIntroduce;
    private List<MemberRoles> memberRoles;
    private String profile;
    private List<SignUpCourse> signUpCourses;
    private Instructor instructor;
    private List<CourseReview> courseReviews;
    private List<CourseQuestion> courseQuestions;
    private Cart cart;
    private List<Payment> payments;
    private List<CourseHistory> courseHistories;
    private List<Notification> notifications;

    private LocalDateTime create_at = LocalDateTime.now();
    private LocalDateTime modify_at = LocalDateTime.now();

    // 편의 메서드 마지막 수강 기록을 조회
    public CourseHistoryDTO getLastHistory(){
        CourseHistory courseHistory = null;
        if(courseHistories.size() > 1){
            courseHistory = courseHistories.stream().skip(courseHistories.size() - 1).findFirst().orElse(null);
            return courseHistory.of();
        }
        return null;
    }

    public boolean isPutInTheCart(Long course_id) {
        if (this.getCart() != null) {
            Course course = this.getCart().getCourses().stream().filter(c ->
                    c.getId() == course_id).findFirst().orElse(null);
            if (course != null) return true;
        }
        return false;
    }

    // 편의 메서드 해당 강의에 수강평을 등록했는지 여부
    public boolean isRegisteredReview(Course course){
        boolean isRegistered = courseReviews.stream().anyMatch(c -> c.getCourse().getId() == course.getId());
        return isRegistered;
    }
}
