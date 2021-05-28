package me.eggme.classh.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseHistoryDTO implements Serializable {
    private Long id;
    private int startTime;
    private int endTime;
    private MemberDTO member;
    private CourseDTO course;
    private CourseClassDTO courseClass;
    private LocalDateTime create_at;
    private LocalDateTime modify_at;
}
