package me.eggme.classh.repository;

import me.eggme.classh.domain.entity.CourseSection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseSessionRepository extends JpaRepository<CourseSection, Long> {
}
