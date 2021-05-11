package me.eggme.classh.repository;

import me.eggme.classh.domain.entity.CourseTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseTagRepository extends JpaRepository<CourseTag, Long> {
}
