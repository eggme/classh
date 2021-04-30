package me.eggme.classh.repository;

import me.eggme.classh.domain.entity.Resources;
import me.eggme.classh.domain.entity.Role;
import me.eggme.classh.domain.entity.RoleResources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleResourcesRepository extends JpaRepository<RoleResources, Long> {

    @Query("select rr from RoleResources rr where rr.role=?1 and rr.resources=?2")
    RoleResources findByRoleAndResources(Role role, Resources resources);
}
