package me.eggme.classh.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@ToString(exclude = {"member", "courses"})
@EqualsAndHashCode(exclude = {"member", "courses"})
public class Instructor {

    @Id @GeneratedValue
    private Long id;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "instructor")
    private List<Course> courses = new ArrayList<>();

    public void setCourses(Course course){
        this.courses.add(course);
        course.setInstructor(this);
    }

}
