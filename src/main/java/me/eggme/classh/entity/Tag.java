package me.eggme.classh.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import me.eggme.classh.entity.Course;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "course")
@EqualsAndHashCode(exclude = "course")
public class Tag {

    @Id @GeneratedValue
    private Long id;

    // 강의의 태그 값
    private String value;

    // 태그에 등록된 강의
    @JsonBackReference
    @ManyToOne
    private Course course;
}
