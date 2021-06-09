package me.eggme.classh.domain.entity;

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
public class CourseHistory extends BaseTimeEntity implements Serializable {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Course course;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private CourseClass courseClass;

    // 시작 시간
    private int startTime;
    // 끝 시간
    private int endTime;

    public CourseHistoryDTO of(){
        return ModelMapperUtils.getModelMapper().map(this, CourseHistoryDTO.class);
    }
}
