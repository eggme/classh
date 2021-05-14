package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import me.eggme.classh.domain.dto.CourseNoticeDTO;
import me.eggme.classh.utils.ModelMapperUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"course", "member", "courseComments"})
@EqualsAndHashCode(exclude = {"course", "member", "courseComments"})
public class CourseNotice extends BaseBoardEntity implements Serializable {

    @Id @GeneratedValue
    private Long id;

    // 공개 범위
    private boolean isPublic;

    // 공지사항 제목
    private String title;

    // 공지사항 내용
    private String notice;

    // 공지사항 쓴 사람
    @JsonBackReference
    @OneToOne(fetch = FetchType.EAGER)
    private Member member;

    // 아떤 강의의 공지사항인지 FK
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;

    // 공지사항의 답글
    @JsonManagedReference
    @OneToMany(mappedBy = "courseNotice", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("create_at asc")
    private List<CourseComment> courseComments = new ArrayList<>();

    public CourseNoticeDTO of(){
        return ModelMapperUtils.getModelMapper().map(this, CourseNoticeDTO.class);
    }
}
