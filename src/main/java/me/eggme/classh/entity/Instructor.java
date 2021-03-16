package me.eggme.classh.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Instructor {

    @Id @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "instructor")
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "instructor")
    private List<Course> courses = new ArrayList<>();

    public void addMember(Member member){
        this.members.add(member);
        member.setInstructor(this);
    }

    public void addCourse(Course course){
        this.courses.add(course);
        course.setInstructor(this);
    }

    public void removeMember(Member member){
        this.members.remove(member);
        member.setInstructor(null);
    }

    public void removeCourse(Course course){
        this.courses.remove(course);
        course.setInstructor(null);
    }

}
