package me.eggme.classh.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@EntityListeners(value = {AuditingEntityListener.class})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "roleResourcesSet")
@ToString(exclude = "roleResourcesSet")
public class Resources extends BaseTimeEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String resourcesName;

    private String httpMethod;

    private int orderNum;

    private String resourcesType;

    @JsonManagedReference
    @OneToMany(mappedBy="resources", fetch = FetchType.EAGER)
    @BatchSize(size = 10)
    private Set<RoleResources> roleResourcesSet = new HashSet<>();
}
