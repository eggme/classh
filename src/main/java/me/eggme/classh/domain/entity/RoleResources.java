package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @JsonBackReference
    @ManyToOne
    private Role role;

    @JsonBackReference
    @ManyToOne
    private Resources resources;
}
