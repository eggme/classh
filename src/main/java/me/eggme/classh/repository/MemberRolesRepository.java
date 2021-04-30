package me.eggme.classh.repository;

import me.eggme.classh.domain.entity.MemberRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRolesRepository extends JpaRepository<MemberRoles, Long> {
}
