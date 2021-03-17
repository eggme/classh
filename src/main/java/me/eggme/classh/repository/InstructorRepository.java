package me.eggme.classh.repository;

import me.eggme.classh.entity.Instructor;
import me.eggme.classh.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    @Query("select i from Instructor i where i.member = :member")
    Instructor findByMember(Member member);
}
