package me.eggme.classh.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"member", "course", "courseComments"})
@EqualsAndHashCode(exclude = {"member", "course", "courseComments"})
public class CourseReview extends BaseBoardEntity{

    @Id @GeneratedValue
    private Long id;

    // 별점
    private int rate = 0;

    // 수강평 내용
    private String reviewContent;

    // 수강평을 남기는 사람
    @JsonBackReference
    @ManyToOne
    private Member member;

    // 수강평이 남겨지는 강의
    @JsonBackReference
    @ManyToOne
    private Course course;

    // 리뷰의 답변
    @JsonManagedReference
    @OneToMany(mappedBy = "courseReview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseComment> courseComments = new ArrayList<>();

}
