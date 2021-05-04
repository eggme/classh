package me.eggme.classh.repository;

import me.eggme.classh.domain.entity.Member;
import me.eggme.classh.domain.entity.MemberRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);
    int countByUsername(String username);

    @Query("select count(m) from Member m join m.memberRoles mr where mr.role.roleName = :roleName")
    int countByMemberRoles(String roleName);
}
