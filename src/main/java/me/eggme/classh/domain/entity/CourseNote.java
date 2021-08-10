package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import me.eggme.classh.domain.dto.CourseNoteDTO;
import me.eggme.classh.utils.ModelMapperUtils;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"member", "course", "courseClass"})
@EqualsAndHashCode(exclude = {"member", "course", "courseClass"})
public class CourseNote extends BaseTimeEntity implements Serializable {

    @Id @GeneratedValue
    private Long id; // pk

    @JsonBackReference
    @ManyToOne
    private Member member; // 노트를 작성한 유저

    @OneToOne
    private Course course; // 연결된 강의

    @OneToOne
    private CourseClass courseClass; // 연결된 강의의 수업

    private String content; // 노트 내용

    private int seconds; // 강의 중에 노트를 작성할 경우 작성된 강의의 시간

    public CourseNoteDTO of(){
        return ModelMapperUtils.getModelMapper().map(this, CourseNoteDTO.class);
    }


}
