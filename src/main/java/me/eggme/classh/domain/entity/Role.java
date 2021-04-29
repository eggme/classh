package me.eggme.classh.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"memberRoles", "roleResources"})
@ToString(exclude = {"memberRoles", "roleResources"})
public class Role implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String roleName;

    private String roleDesc;

    @ManyToOne
    private MemberRoles memberRoles;

    @OneToMany(mappedBy = "role")
    private Set<RoleResources> roleResources = new HashSet<>();

}
