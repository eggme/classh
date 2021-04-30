package me.eggme.classh.repository;

import me.eggme.classh.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);

    @Query("select r from Role r where r.roleName='ROLE_USER'")
    Role defaultUserRole();
}
