package me.eggme.classh.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString(exclude = {"parent", "children"})
@EqualsAndHashCode(exclude = {"parent", "children"})
public class CourseComment extends BaseBoardEntity{

    @Id @GeneratedValue
    private Long id;

    // 댓글 내용
    private String commentContent;

    // 댓글 단 사람 정보
    @OneToOne
    private Member member;

    // 댓글의 부모
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private CourseComment parent;

    // 댓글의 자식들
    @JsonManagedReference
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseComment> children = new ArrayList<>();

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
}
