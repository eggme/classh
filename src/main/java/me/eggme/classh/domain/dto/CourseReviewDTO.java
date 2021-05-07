package me.eggme.classh.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.eggme.classh.domain.entity.Course;
import me.eggme.classh.domain.entity.CourseComment;
import me.eggme.classh.domain.entity.Member;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseReviewDTO implements Serializable {
    private long id;
    private int rate;
    private String reviewContent;
    private Member member;
    private Course course;
    private List<CourseComment> courseComments;
}
