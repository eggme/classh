package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import me.eggme.classh.domain.dto.QuestionStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/***
 *  강의 질문&답변 댓글과 답글을 어떻게 구분할 것인가?
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"members", "course", "courseClass", "member", "courseComments", "courseTags"})
@EqualsAndHashCode(exclude = {"members", "course", "courseClass", "member", "courseComments", "courseTags"})
public class CourseQuestion extends BaseBoardEntity implements Serializable {

    @Id @GeneratedValue
    private Long id;

    // 질문의 제목
    private String title;

    // 질문의 내용
    private String content;

    // 질문의 해결 상태
    @Enumerated(value = EnumType.STRING)
    private QuestionStatus questionStatus = QuestionStatus.UNRESOLVED;

    // 어느 강의에 질문인지
    @JsonBackReference
    @ManyToOne
    public Course course;

    // 어떤 수업에 질문인지
    @JsonBackReference
    @ManyToOne
    public CourseClass courseClass;

    // 질문을 쓴 사람이 누구인지
    @JsonBackReference
    @ManyToOne
    private Member member;

    // 질문답변의 답글
    @JsonManagedReference
    @OneToMany(mappedBy = "courseQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseComment> courseComments = new ArrayList<>();

    // 달릴 해시태그들 1:N 단방향
    @JsonManagedReference
    @OneToMany(mappedBy = "courseQuestion",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseTag> courseTags = new ArrayList<>();
}
