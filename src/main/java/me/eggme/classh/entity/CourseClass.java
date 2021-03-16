package me.eggme.classh.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class CourseClass {

    @Id @GeneratedValue
    private Long id;

    // 수업 제목
    private String name;

    // 수업 영상 경로
    private String mediaPath;

    // 무료 영상 공개
    private boolean isPublic = false;

    // 자료 파일 경로
    private String dataPath;

    // 강의 시간
    private int seconds;

    // 강사가 남기는 메모
    private String instructorMemo;

    @ManyToOne
    private CourseSection courseSection;
}
