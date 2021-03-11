package me.eggme.classh.entity;

import lombok.*;
import me.eggme.classh.security.UserRole;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Setter
    @Id @GeneratedValue
    private Long id;

    @Setter
    @Column(length = 50, nullable = false)
    private String email;

    @Setter
    @Column(length = 20, nullable = false)
    private String name;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime create_at;

    @UpdateTimestamp
    @Column
    private LocalDateTime update_at;

    @Setter
    @Column(length = 512, nullable = false)
    private String password;

    @Setter
    @Column(nullable = false)
    private boolean isEnable = true;

    @Setter
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    @Setter
    @Column(nullable = false)
    private String profile = "/imgs/mini_icon_1.png";

    @Builder
    public Member(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
