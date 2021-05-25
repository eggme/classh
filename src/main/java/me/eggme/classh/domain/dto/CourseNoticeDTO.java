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
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseNoticeDTO implements Serializable {
    private Long id;
    private boolean isPublic;
    private String title;
    private String notice;
    private Member member;
    private Course course;
    private Set<CourseComment> courseComments = new LinkedHashSet<>();
    private LocalDateTime create_at = LocalDateTime.now();
    private LocalDateTime modify_at = LocalDateTime.now();

    public int commentSize(){
        if(courseComments == null) return 0;
        return courseComments.size();
    }
}
