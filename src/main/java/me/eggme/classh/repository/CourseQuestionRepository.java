package me.eggme.classh.repository;

import me.eggme.classh.domain.entity.CourseQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseQuestionRepository extends JpaRepository<CourseQuestion, Long> {

    @Query("select cq from CourseQuestion cq join fetch cq.courseComments where cq.id=:id")
    Optional<CourseQuestion> findByIdAndComments(Long id);

}
