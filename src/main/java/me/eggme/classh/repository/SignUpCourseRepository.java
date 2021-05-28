package me.eggme.classh.repository;

import me.eggme.classh.domain.entity.Course;
import me.eggme.classh.domain.entity.Member;
import me.eggme.classh.domain.entity.SignUpCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignUpCourseRepository extends JpaRepository<SignUpCourse, Long> {

    SignUpCourse findByCourseAndMember(Course course, Member member);
}
