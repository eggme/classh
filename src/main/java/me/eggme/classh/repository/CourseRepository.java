package me.eggme.classh.repository;

import me.eggme.classh.domain.dto.CourseState;
import me.eggme.classh.domain.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByNameContainingIgnoreCase(String value);

    @Query("select distinct new Course(c.id, c.name, i, c.courseImg, c.price, c.courseCategory, c.courseState) " +
            "from Course c join c.courseTags t left join c.instructor i " +
            "where upper(c.name) like concat('%', upper(:value),'%') " +
            "or upper(i.member.nickName) like concat('%', upper(:value),'%') " +
            "or upper(t.tag) like concat('%', upper(:value),'%') " +
            "and c.courseState = :courseState")
    Set<Course> findByCourse(String value, CourseState courseState);

    List<Course> findTop12ByCourseState(CourseState courseState);
    
    List<Course> findTop4ByCourseState(CourseState courseState);
}
