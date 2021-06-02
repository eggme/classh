package me.eggme.classh.domain.entity;

import lombok.*;
import me.eggme.classh.domain.dto.NotificationType;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"writter", "member", "courseComments"})
@EqualsAndHashCode(exclude = {"writter", "member", "courseComments"})
public class Notification extends BaseTimeEntity implements Serializable {

    @Id @GeneratedValue
    private Long id;

    // 누가 썼는지?
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="WRITTER_ID")
    private Member writter;

    /* 누구에게 썼는지 */
    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;

    @Column(length = 500)
    private String title;

    @Column(columnDefinition = "CLOB")
    private String content;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("modify_at asc")
    @BatchSize(size = 10)
    private Set<CourseComment> courseComments = new HashSet<>();

    @Enumerated(value = EnumType.STRING)
    private NotificationType notificationType;
}
