package me.eggme.classh.repository;

import me.eggme.classh.domain.entity.CourseComment;
import me.eggme.classh.domain.entity.CourseQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseCommentRepository extends JpaRepository<CourseComment, Long> {
    @Query("select cc from CourseComment cc where cc.courseQuestion=:courseQuestion order by cc.modify_at asc")
    List<CourseComment> findByCourseQuestion(CourseQuestion courseQuestion);
}
