package me.eggme.classh.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.eggme.classh.domain.entity.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseQuestionDTO implements Serializable {

    private Long id;
    private String title;
    private String content;
    private QuestionStatus questionStatus;
    private Course course;
    private CourseClass courseClass;
    private Member member;
    private Set<CourseComment> courseComments;
    private Set<CourseTag> courseTags;
    private LocalDateTime create_at = LocalDateTime.now();
    private LocalDateTime modify_at = LocalDateTime.now();

    /***
     *  총 댓글 수
     */
    public int getCommentSize(){
        if(courseComments == null) return 0;
        return courseComments.size();
    }
}
