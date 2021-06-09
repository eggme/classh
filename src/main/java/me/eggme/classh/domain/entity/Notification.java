package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import me.eggme.classh.domain.dto.NotificationDTO;
import me.eggme.classh.domain.dto.NotificationType;
import me.eggme.classh.utils.ModelMapperUtils;
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
@ToString(exclude = {"writer", "member", "courseComments"})
@EqualsAndHashCode(exclude = {"writer", "member", "courseComments"})
public class Notification extends BaseTimeEntity implements Serializable {

    @Id @GeneratedValue
    private Long id;

    // 누가 썼는지?
    @JsonBackReference
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="WRITER_ID")
    private Member writer;

    /* 누구에게 썼는지 */
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;

    /* 읽었는지 확인하는 변수 */
    private boolean isRead = false;

    /* 어떤 게시글을 링크할 것인가 */
    private Long target = 0L;

    @Column(length = 500)
    private String title;

    @Column(columnDefinition = "CLOB")
    private String content;

    @JsonManagedReference
    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("modify_at asc")
    @BatchSize(size = 10)
    private Set<CourseComment> courseComments = new HashSet<>();

    @Enumerated(value = EnumType.STRING)
    private NotificationType notificationType;

    public NotificationDTO of(){
        NotificationDTO notificationDTO = ModelMapperUtils.getModelMapper().map(this, NotificationDTO.class);
        notificationDTO.setWriter(this.getWriter().of());
        notificationDTO.setMember(this.getMember().of());
        notificationDTO.setNotificationType(this.getNotificationType());
        return notificationDTO;
    }
}
