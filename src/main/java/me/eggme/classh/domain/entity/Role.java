package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
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
@EqualsAndHashCode(exclude = {"memberRoles", "roleResources"})
@ToString(exclude =  {"memberRoles", "roleResources"})
public class Role implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String roleName;

    private String roleDesc;

    @JsonManagedReference
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    @BatchSize(size = 10)
    private Set<MemberRoles> memberRoles = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    @BatchSize(size = 10)
    private Set<RoleResources> roleResources = new HashSet<>();

}
