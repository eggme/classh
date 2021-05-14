package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import me.eggme.classh.domain.dto.CourseCommentDTO;
import me.eggme.classh.utils.ModelMapperUtils;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"member", "parent", "children", "courseQuestion", "courseNotice", "courseReview"})
@EqualsAndHashCode(exclude = {"member","parent", "children", "courseQuestion", "courseNotice", "courseReview"})
public class CourseComment extends BaseBoardEntity implements Serializable {

    @Id @GeneratedValue
    private Long id;

    // 댓글 내용
    private String commentContent;

    // 댓글 단 사람 정보
    @JsonBackReference
    @OneToOne(fetch = FetchType.EAGER)
    private Member member;

    // 댓글의 부모
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PARENT_ID")
    private CourseComment parent;

    // 댓글의 자식들
    @JsonManagedReference
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("create_at asc")
    @BatchSize(size = 10)
    private List<CourseComment> children = new ArrayList<>();

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "COURSE_QUESTION_ID")
    private CourseQuestion courseQuestion;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "COURSE_NOTICE_ID")
    private CourseNotice courseNotice;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "COURSE_REVIEW_ID")
    private CourseReview courseReview;

    public CourseCommentDTO of() {
        return ModelMapperUtils.getModelMapper().map(this, CourseCommentDTO.class);
    }
}
