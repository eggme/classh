package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.BatchSize;

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
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    private Member member;

    @JsonManagedReference
    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id asc")
    @BatchSize(size = 10)
    private List<Course> courses = new ArrayList<>();

    public void setCourses(Course course){
        this.courses.add(course);
        course.setInstructor(this);
    }

    public int totalCourseCount(){
        return this.courses.size();
    }
}
