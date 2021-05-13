package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import me.eggme.classh.domain.dto.CourseDTO;
import me.eggme.classh.domain.dto.CourseReviewDTO;
import me.eggme.classh.domain.dto.CourseSectionDTO;
import me.eggme.classh.utils.ModelMapperUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"member", "course", "courseComments"})
@EqualsAndHashCode(exclude = {"member", "course", "courseComments"})
public class CourseReview extends BaseBoardEntity implements Serializable {

    @Id @GeneratedValue
    private Long id;

    // 별점
    private int rate = 0;

    // 수강평 내용
    private String reviewContent;

    // 수강평을 남기는 사람
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;

    // 수강평이 남겨지는 강의
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;

    // 리뷰의 답변
    @JsonManagedReference
    @OneToMany(mappedBy = "courseReview", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CourseComment> courseComments = new ArrayList<>();

    public CourseReviewDTO of(){
        return ModelMapperUtils.getModelMapper().map(this, CourseReviewDTO.class);
    }

    public boolean isWroteReview(Member member){
        if(getMember().getId() == member.getId()){
            return true;
        }
        return false;
    }

}
