package me.eggme.classh.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class CourseReview extends BaseTimeEntity{

    @Id @GeneratedValue
    private Long id;

    // 별점
    private int late;

    // 수강평을 남기는 사람
    @ManyToOne
    private Member member;

    // 수강평이 남겨지는 강의
    @ManyToOne
    private Course course;
}
