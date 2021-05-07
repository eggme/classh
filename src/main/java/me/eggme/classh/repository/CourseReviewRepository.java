package me.eggme.classh.repository;

import me.eggme.classh.domain.entity.CourseReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseReviewRepository extends JpaRepository<CourseReview, Long> {
}
