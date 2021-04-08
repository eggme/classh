package me.eggme.classh.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = "members")
@EqualsAndHashCode(exclude = "members")
public abstract class BaseBoardEntity {

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="MEMBER_ID")
    private List<Member> members = new ArrayList<>();

    @CreatedDate
    private LocalDateTime create_at;

    @LastModifiedDate
    private LocalDateTime modify_at;

    public int getLikeCount(){
        return this.members.size();
    }
}
