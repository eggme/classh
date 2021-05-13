package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import me.eggme.classh.domain.dto.CourseDTO;
import me.eggme.classh.domain.dto.CourseQuestionDTO;
import me.eggme.classh.domain.dto.QuestionStatus;
import me.eggme.classh.utils.ModelMapperUtils;
import org.springframework.core.annotation.Order;

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
@ToString(exclude = {"course", "courseClass", "member", "courseComments", "courseTags"})
@EqualsAndHashCode(exclude = {"course", "courseClass", "member", "courseComments", "courseTags"})
public class CourseQuestion extends BaseBoardEntity implements Serializable {

    @Id @GeneratedValue
    private Long id;

    // 질문의 제목
    private String title;

    // 질문의 내용
    @Column(length = 3000)
    private String content;

    // 질문의 해결 상태
    @Enumerated(value = EnumType.STRING)
    private QuestionStatus questionStatus = QuestionStatus.UNRESOLVED;

    // 어느 강의에 질문인지
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;

    // 어떤 수업에 질문인지
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    private CourseClass courseClass;

    // 질문을 쓴 사람이 누구인지
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;

    // 질문답변의 답글
    @JsonManagedReference
    @OneToMany(mappedBy = "courseQuestion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("create_at desc")
    private List<CourseComment> courseComments = new ArrayList<>();

    // 달릴 해시태그들 1:N 단방향
    @JsonManagedReference
    @OneToMany(mappedBy = "courseQuestion",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseTag> courseTags = new ArrayList<>();

    public CourseQuestionDTO of(){
        CourseQuestionDTO courseQuestionDTO = ModelMapperUtils.getModelMapper().map(this, CourseQuestionDTO.class);
        return courseQuestionDTO;
    }

    public CourseQuestionDTO ofWithOutTag(){
        CourseQuestionDTO courseQuestionDTO = ModelMapperUtils.getModelMapper().map(this, CourseQuestionDTO.class);
        courseQuestionDTO.setContent(courseQuestionDTO.getContent().replaceAll("\\<.*?\\>",""));
        courseQuestionDTO.setContent(courseQuestionDTO.getContent().replace("&bull;",""));
        return courseQuestionDTO;
    }


    public void setMember(Member member){
        this.member = member;
        member.getCourseQuestions().add(this);
    }

    public void setCourse(Course course){
        this.course = course;
        course.getCourseQuestions().add(this);
    }

    public void setCourseClass(CourseClass courseClass){
        this.courseClass = courseClass;
        courseClass.getCourseQuestions().add(this);
    }
}
