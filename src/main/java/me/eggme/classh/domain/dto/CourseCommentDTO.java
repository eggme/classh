package me.eggme.classh.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.eggme.classh.domain.entity.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseCommentDTO implements Serializable {
    private Long id;
    private String commentContent;
    private Member member;
    private CourseComment parent;
    private List<CourseComment> children;
    private CourseQuestion courseQuestion;
    private CourseNotice courseNotice;
    private CourseReview courseReview;
}
