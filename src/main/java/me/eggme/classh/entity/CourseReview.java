package me.eggme.classh.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"member", "course"})
@EqualsAndHashCode(exclude = {"member", "course"})
public class CourseReview extends BaseTimeEntity{

    @Id @GeneratedValue
    private Long id;

    // 별점
    private int late = 0;

    // 좋아요
    private int userLike = 0;

    // 수강평을 남기는 사람
    @JsonBackReference
    @ManyToOne
    private Member member;

    // 수강평이 남겨지는 강의
    @JsonBackReference
    @ManyToOne
    private Course course;
}
