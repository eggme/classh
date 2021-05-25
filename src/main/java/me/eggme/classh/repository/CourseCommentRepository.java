package me.eggme.classh.repository;

import me.eggme.classh.domain.entity.CourseComment;
import me.eggme.classh.domain.entity.CourseQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CourseCommentRepository extends JpaRepository<CourseComment, Long> {
    @Query("select distinct cc from CourseComment cc join fetch cc.courseQuestion where cc.courseQuestion=:courseQuestion and cc.parent is null order by cc.modify_at asc")
    Set<CourseComment> findByCourseQuestion(CourseQuestion courseQuestion);
}
