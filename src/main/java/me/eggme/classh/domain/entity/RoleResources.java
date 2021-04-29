package me.eggme.classh.domain.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/***
 *  권한 별 접근 가능한 리소스를 정의한 테이블 (연결테이블)
 */

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"role", "resources"})
@ToString(exclude = {"role", "resources"})
public class RoleResources implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Role role;

    @ManyToOne
    private Resources resources;
}
