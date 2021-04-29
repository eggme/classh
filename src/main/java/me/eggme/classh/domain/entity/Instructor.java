package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@ToString(exclude = {"member", "courses"})
@EqualsAndHashCode(exclude = {"member", "courses"})
public class Instructor implements Serializable {

    @Id @GeneratedValue
    private Long id;

    @JsonBackReference
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @JsonManagedReference
    @OneToMany(mappedBy = "instructor")
    private List<Course> courses = new ArrayList<>();

    public void setCourses(Course course){
        this.courses.add(course);
        course.setInstructor(this);
    }

}
