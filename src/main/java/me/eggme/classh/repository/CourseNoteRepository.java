package me.eggme.classh.repository;

import me.eggme.classh.domain.entity.CourseClass;
import me.eggme.classh.domain.entity.CourseNote;
import me.eggme.classh.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseNoteRepository extends JpaRepository<CourseNote, Long> {

    List<CourseNote> findAllByMemberAndCourseClassOrderByIdAsc(Member member, CourseClass courseClass);
}
