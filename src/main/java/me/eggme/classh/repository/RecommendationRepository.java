package me.eggme.classh.repository;

import me.eggme.classh.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    void deleteAllByCourseId(Long id);
}
