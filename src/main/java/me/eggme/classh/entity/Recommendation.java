package me.eggme.classh.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

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
public class Recommendation {

    @Id
    @GeneratedValue
    private Long id;

    // 추천하는 사람의 종류
    private String value;

    // 추천하는 강의
    @JsonBackReference
    @ManyToOne
    private Course course;
}
