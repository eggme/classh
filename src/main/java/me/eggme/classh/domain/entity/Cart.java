package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"member", "courses"})
@EqualsAndHashCode(exclude = {"member", "courses"})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Cart extends BaseTimeEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @JsonBackReference
    @OneToOne
    private Member member;

    @JsonManagedReference
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "COURSE_ID")
    private Set<Course> courses = new LinkedHashSet<>();

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public void deleteCarts() {
        this.getMember().setCart(null);
        this.setMember(null);
        this.setCourses(null);
    }
}
