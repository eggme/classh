package me.eggme.classh.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // 이걸 달아야 AuditingEntityListener에게 관리 받을 수 있음
public abstract class BaseTimeEntity {

    // Entity 가 생성되어 저장될 때 시간이 자동으로 저장됨
    @CreatedDate
    private LocalDateTime create_at;

    // 조회된 Entity 값을 변경할 때 시간이 자동 저장
    @LastModifiedDate
    private LocalDateTime modify_at;
}
