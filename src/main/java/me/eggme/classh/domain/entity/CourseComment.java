package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import me.eggme.classh.domain.dto.CourseCommentDTO;
import me.eggme.classh.utils.ModelMapperUtils;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"member", "parent", "children", "courseQuestion", "courseNotice", "courseReview"})
@EqualsAndHashCode(exclude = {"member","parent", "children", "courseQuestion", "courseNotice", "courseReview"})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class CourseComment extends BaseBoardEntity implements Serializable {

    @Id @GeneratedValue
    private Long id;

    // 댓글 내용
    private String commentContent;

    // 댓글 단 사람 정보
    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    // 댓글의 부모
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private CourseComment parent;

    // 댓글의 자식들
    @JsonManagedReference
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("create_at asc")
    @BatchSize(size = 10)
    private Set<CourseComment> children = new LinkedHashSet<>();

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "COURSE_QUESTION_ID")
    private CourseQuestion courseQuestion;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "COURSE_NOTICE_ID")
    private CourseNotice courseNotice;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "COURSE_REVIEW_ID")
    private CourseReview courseReview;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "COURSE_ALARM_ID")
    private Notification notification;

    public CourseCommentDTO of() {
        return ModelMapperUtils.getModelMapper().map(this, CourseCommentDTO.class);
    }

    /* 연관관계 편의 메서드 */

    public void deleteCourseComment() {
        if(this.getParent() != null) this.setParent(null);
        if(this.getChildren() != null) this.getChildren().stream().forEach(cc -> cc.setParent(null));
        this.setCourseNotice(null);
        this.setCourseReview(null);
        this.setCourseQuestion(null);
    }
}
