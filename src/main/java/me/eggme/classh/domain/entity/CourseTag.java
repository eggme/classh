package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/***
 * 강의의 자체 태그나, 강의 질문에 들어갈 태그
 */
@Entity
@Getter
@Setter
@ToString(exclude = {"course", "courseQuestion"})
@EqualsAndHashCode(exclude = {"course", "courseQuestion"})
public class CourseTag implements Serializable {

    @Id @GeneratedValue
    private Long id;

    public String tag;

    @JsonBackReference
    @ManyToOne // 강의에 대한 태그
    private Course course;

    @JsonBackReference
    @ManyToOne // 강의 질문에 대한 태그
    private CourseQuestion courseQuestion;

    /* 연관관계 편의 메서드 */
    public void deleteCourseTag() {
        this.setCourseQuestion(null);
        this.setCourse(null);
    }
}
