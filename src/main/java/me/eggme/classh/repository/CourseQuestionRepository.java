package me.eggme.classh.repository;

import me.eggme.classh.domain.entity.CourseQuestion;
import me.eggme.classh.domain.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseQuestionRepository extends JpaRepository<CourseQuestion, Long> {

    @Query("select distinct cq from CourseQuestion cq join fetch cq.courseComments where cq.id=:id")
    Optional<CourseQuestion> findByIdAndComments(Long id);

    Page<CourseQuestion> findAllByMember(Pageable pageable, Member member);

}
