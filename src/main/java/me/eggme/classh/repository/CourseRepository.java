package me.eggme.classh.repository;

import me.eggme.classh.domain.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("select c from Course c where c.url = :url")
    Optional<Course> findByUrl(String url);

}
