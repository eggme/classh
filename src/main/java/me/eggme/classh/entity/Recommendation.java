package me.eggme.classh.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.eggme.classh.entity.Course;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor
public class Recommendation {

    @Id @GeneratedValue
    private Long id;

    // 추천하는 사람의 종류
    private String value;

    // 추천하는 강의
    @ManyToOne
    private Course course;
}
