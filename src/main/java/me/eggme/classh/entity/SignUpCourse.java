package me.eggme.classh.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"member", "course"})
@EqualsAndHashCode(exclude = {"member", "course"})
public class SignUpCourse {

    @Id @GeneratedValue
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;
}
