package me.eggme.classh.domain.dto;

import lombok.*;
import me.eggme.classh.domain.entity.*;

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
}
