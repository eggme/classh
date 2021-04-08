package me.eggme.classh.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import me.eggme.classh.entity.Course;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/***
 *  강의 등록 시 스킬태그에 들어갈 목록
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "course")
@EqualsAndHashCode(exclude = "course")
public class SkillTag {

    @Id @GeneratedValue
    private Long id;

    // 강의의 태그 값
    private String value;

    // 태그에 등록된 강의
    @JsonBackReference
    @ManyToOne
    private Course course;
}
