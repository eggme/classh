package me.eggme.classh.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.eggme.classh.entity.*;

import java.util.List;

@Getter
@Setter
@ToString
public class CourseDTO {
    private Long id;
    private String name;
    private int price;
    private String url;
    private CourseState courseState;
    private List<SignUpCourse> signUpCourses;
    private Instructor instructor;
    private List<CourseSection> courseSections;
    private List<Tag> tags;
    private List<Recommendation> recommendations;
    private CourseLevel courseLevel;
    private CourseCategory courseCategory;
    private String shortDesc;
    private String longDesc;
    private String courseImg;
    private List<CourseReview> courseReviews;

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
