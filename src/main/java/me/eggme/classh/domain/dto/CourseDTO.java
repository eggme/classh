package me.eggme.classh.domain.dto;

import lombok.*;
import me.eggme.classh.domain.entity.*;
import me.eggme.classh.utils.CourseValidation;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO implements Serializable {
    private Long id;
    private String name;
    private int price;
    private String url;
    private CourseState courseState;
    private List<SignUpCourse> signUpCourses;
    private Instructor instructor;
    private List<CourseSection> courseSections;
    private List<SkillTag> skillTags;
    private List<Recommendation> recommendations;
    private CourseLevel courseLevel;
    private CourseCategory courseCategory;
    private String shortDesc;
    private String longDesc;
    private String courseImg;
    private List<CourseReview> courseReviews;
    private LocalDateTime create_at = LocalDateTime.now();
    private LocalDateTime modify_at = LocalDateTime.now();
    private CourseValidation courseValidation;

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

    // '제출' 상태 이면 false 반환
    public boolean isSubmitted(){
        if(getCourseState().getValue().equals(CourseState.SUBMIT.getValue())){
            return true;
        }
        return false;
    }

    // 해당강의에 수강평을 썼는지 검증
    public boolean isWroteReview(Member member){
        for(CourseReview review : courseReviews){
            if(review.getMember().getId() == member.getId()){
                return true;
            }
        }
        return false;
    }
}
