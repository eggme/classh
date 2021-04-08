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
@ToString(exclude = {"course", "member", "courseComments"})
@EqualsAndHashCode(exclude = {"course", "member", "courseComments"})
public class CourseNotice extends BaseBoardEntity{

    @Id @GeneratedValue
    private Long id;

    // 공개 범위
    private boolean isPublic;

    // 공지사항 제목
    public String title;

    // 공지사항 내용
    public String notice;

    // 공지사항 쓴 사람
    @OneToOne
    private Member member;

    // 아떤 강의의 공지사항인지 FK
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

    // 공지사항의 답글
    @JsonManagedReference
    @OneToMany(mappedBy = "courseNotice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseComment> courseComments = new ArrayList<>();

}
