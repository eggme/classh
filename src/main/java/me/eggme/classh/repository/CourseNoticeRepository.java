package me.eggme.classh.repository;

import me.eggme.classh.domain.entity.Course;
import me.eggme.classh.domain.entity.CourseNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseNoticeRepository extends JpaRepository<CourseNotice, Long> {

    @Query("select cn from CourseNotice cn where cn.course = :course order by cn.create_at asc")
    List<CourseNotice> findAllByCourse(Course course);
}
