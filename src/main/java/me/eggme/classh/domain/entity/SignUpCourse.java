package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"member", "course"})
@EqualsAndHashCode(exclude = {"member", "course"})
public class SignUpCourse implements Serializable {

    @Id @GeneratedValue
    private Long id;

    @JsonBackReference
    @ManyToOne
    private Member member;

    @JsonBackReference
    @ManyToOne
    private Course course;

    /* 연관관계 편의 메서드 */
    public void deleteSignUpCourse(){
        this.setMember(null);
        this.setCourse(null);
    }
}
