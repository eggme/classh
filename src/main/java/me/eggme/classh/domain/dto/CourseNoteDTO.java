package me.eggme.classh.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.eggme.classh.domain.entity.Course;
import me.eggme.classh.domain.entity.CourseClass;
import me.eggme.classh.domain.entity.Member;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseNoteDTO implements Serializable {
    private Long id; // pk
    private Member member; // 노트를 작성한 유저
    private Course course; // 연결된 강의
    private CourseClass courseClass; // 연결된 강의의 수업
    private String content; // 노트 내용
    private int seconds; // 강의 중에 노트
    private LocalDateTime create_at = LocalDateTime.now();
    private LocalDateTime modify_at = LocalDateTime.now();
}
