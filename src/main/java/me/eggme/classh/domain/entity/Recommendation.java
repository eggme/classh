package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "course")
@EqualsAndHashCode(exclude = "course")
public class Recommendation implements Serializable {

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
