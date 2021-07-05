package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import me.eggme.classh.domain.dto.CourseDTO;
import me.eggme.classh.domain.dto.CourseQuestionDTO;
import me.eggme.classh.domain.dto.QuestionStatus;
import me.eggme.classh.utils.ModelMapperUtils;
import org.hibernate.annotations.BatchSize;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/***
 *  강의 질문&답변 댓글과 답글을 어떻게 구분할 것인가?
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"course", "courseClass", "member", "courseComments", "courseTags"})
@EqualsAndHashCode(exclude = {"course", "courseClass", "member", "courseComments", "courseTags"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
    @OneToMany(mappedBy = "courseQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("create_at desc")
    @BatchSize(size = 10)
    private List<CourseComment> courseComments = new ArrayList<>();

    // 달릴 해시태그들 1:N 단방향
    @JsonManagedReference
    @OneToMany(mappedBy = "courseQuestion",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseTag> courseTags = new ArrayList<>();

    public CourseQuestionDTO of(){
        CourseQuestionDTO courseQuestionDTO = ModelMapperUtils.getModelMapper().map(this, CourseQuestionDTO.class);
        courseQuestionDTO.setMember(this.getMember().of());
        if(this.getCourseComments() != null) {
            courseQuestionDTO.setCourseComments(this.getCourseComments().stream().map(cc ->
                    cc.of()).collect(Collectors.toList()));
        }
        return courseQuestionDTO;
    }

    public CourseQuestionDTO ofWithOutTag(){
        CourseQuestionDTO courseQuestionDTO = ModelMapperUtils.getModelMapper().map(this, CourseQuestionDTO.class);
        courseQuestionDTO.setContent(courseQuestionDTO.getContent().replaceAll("\\<.*?\\>",""));
        courseQuestionDTO.setContent(courseQuestionDTO.getContent().replace("&bull;",""));
        return courseQuestionDTO;
    }

    /* 연관관계 편의 메서드 */

    public void deleteCourseQuestion(){
        this.setCourse(null);
        this.setMember(null);
        this.setCourseClass(null);
        if(this.getCourseTags() != null) this.getCourseTags().stream().forEach(ct -> ct.deleteCourseTag());
        if(this.getCourseComments() != null) this.getCourseComments().stream().forEach(cc -> cc.deleteCourseComment());
    }

    public void addCourseTag(CourseTag courseTag){
        System.out.println(courseTag.toString());
        this.getCourseTags().add(courseTag);
    }

    public void setMember(Member member){
        this.member = member;
        if(member != null) member.getCourseQuestions().add(this);
    }

    public void setCourse(Course course){
        this.course = course;
        if( course != null) course.getCourseQuestions().add(this);
    }

    public void setCourseClass(CourseClass courseClass){
        this.courseClass = courseClass;
        if(courseClass != null) courseClass.getCourseQuestions().add(this);
    }
}
