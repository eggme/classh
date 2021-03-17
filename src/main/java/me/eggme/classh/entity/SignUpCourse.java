package me.eggme.classh.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"member", "courses"})
@EqualsAndHashCode(exclude = {"member", "courses"})
public class SignUpCourse {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "signUpCourse")
    private List<Course> courses = new ArrayList<>();
}
