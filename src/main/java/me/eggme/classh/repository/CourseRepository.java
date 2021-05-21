package me.eggme.classh.repository;

import me.eggme.classh.domain.dto.CourseState;
import me.eggme.classh.domain.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByNameContainingIgnoreCase(String value);

    @Query("select c from Course c where c.url = :url")
    Optional<Course> findByUrl(String url);

    List<Course> findTop12ByCourseState(CourseState courseState);
}
