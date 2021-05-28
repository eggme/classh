package me.eggme.classh.repository;

import me.eggme.classh.domain.entity.Course;
import me.eggme.classh.domain.entity.CourseClass;
import me.eggme.classh.domain.entity.CourseHistory;
import me.eggme.classh.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseHistoryRepository extends JpaRepository<CourseHistory, Long> {

    @Query("select ch from CourseHistory ch join fetch ch.courseClass where ch.courseClass=:courseClass and ch.member = :member")
    CourseHistory findByMemberAndCourseClass(Member member, CourseClass courseClass);

    @Query("select ch from CourseHistory ch join fetch ch.course where ch.course=:course and ch.member = :member")
    List<CourseHistory> findAllByMemberAndCourse(Member member, Course course);
}
