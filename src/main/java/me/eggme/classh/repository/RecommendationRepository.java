package me.eggme.classh.repository;

import me.eggme.classh.domain.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    void deleteAllByCourseId(Long id);
}
