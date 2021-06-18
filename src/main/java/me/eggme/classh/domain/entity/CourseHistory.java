package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import me.eggme.classh.domain.dto.CourseHistoryDTO;
import me.eggme.classh.utils.ModelMapperUtils;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"member", "course", "courseClass"})
@ToString(exclude = {"member", "course", "courseClass"})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class CourseHistory extends BaseTimeEntity implements Serializable {

    @Id @GeneratedValue
    private Long id;

    @JsonBackReference
    @ManyToOne
    private Member member;

    @JsonBackReference
    @OneToOne
    @JoinColumn
    private Course course;

    @OneToOne
    @JoinColumn
    @JsonBackReference
    private CourseClass courseClass;

    // 시작 시간
    private int startTime;
    // 끝 시간
    private int endTime;

    public CourseHistoryDTO of(){
        return ModelMapperUtils.getModelMapper().map(this, CourseHistoryDTO.class);
    }
}
