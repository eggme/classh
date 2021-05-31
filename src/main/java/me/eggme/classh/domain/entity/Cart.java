package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"member", "courses"})
@EqualsAndHashCode(exclude = {"member", "courses"})
public class Cart extends BaseTimeEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "COURSE_ID")
    private Set<Course> courses = new LinkedHashSet<>();

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public void deleteCart(Member member, Course course){
        this.getCourses().remove(course);
        member.setCart(this);
    }

    public void deleteCarts() {
        this.getMember().setCart(null);
        this.setMember(null);
        this.setCourses(null);
    }
}
