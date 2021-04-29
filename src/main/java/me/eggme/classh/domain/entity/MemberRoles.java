package me.eggme.classh.domain.entity;

import lombok.*;
import org.hibernate.mapping.ToOne;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberRoles implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Member member;

    @OneToMany(mappedBy = "memberRoles")
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role){
        this.roles.add(role);
        role.setMemberRoles(this);
    }

}
