package me.eggme.classh.repository;

import me.eggme.classh.domain.entity.CourseClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CourseClassRepository extends JpaRepository<CourseClass, Long> {

    @Transactional
    @Modifying
    @Query("delete from CourseClass cc where cc.id=:id")
    void deleteById(Long id);
}
