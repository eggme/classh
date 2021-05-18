package me.eggme.classh.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.eggme.classh.domain.entity.Course;
import me.eggme.classh.domain.entity.CourseComment;
import me.eggme.classh.domain.entity.Member;

import java.io.Serializable;
import java.time.LocalDateTime;
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
    private LocalDateTime create_at = LocalDateTime.now();
    private LocalDateTime modify_at = LocalDateTime.now();

    public boolean isWroteReview(Member member){
        if(getMember().getId() == member.getId()){
            return true;
        }
        return false;
    }
}
