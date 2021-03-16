package me.eggme.classh.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class SignUpCourse {

    @Id @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "signUpCourse")
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "signUpCourse")
    private List<Course> courses = new ArrayList<>();
}
