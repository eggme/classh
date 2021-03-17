package me.eggme.classh.service;

import lombok.extern.log4j.Log4j2;
import me.eggme.classh.entity.Course;
import me.eggme.classh.entity.Instructor;
import me.eggme.classh.entity.Member;
import me.eggme.classh.exception.EmailExistedException;
import me.eggme.classh.exception.NoSearchCourseListException;
import me.eggme.classh.repository.CourseRepository;
import me.eggme.classh.repository.InstructorRepository;
import me.eggme.classh.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Transactional
    public Course createCourseDefault(String courseName, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EmailExistedException(email));
        Course course = new Course(courseName);
        Course newCourse = courseRepository.save(course);
        if(isRegisteredInstructor(member)){
            member.getInstructor().setCourses(newCourse);
        }else{
            Instructor instructor = saveInstructor(member, newCourse);
            member.setInstructor(instructor);
        }
        return newCourse;
    }

    private boolean isRegisteredInstructor(Member member){
        Instructor instructor = instructorRepository.findByMember(member);
        if(instructor == null){
            return false;
        }
        return true;
    }

    @Transactional
    protected Instructor saveInstructor(Member member, Course course) {
        Instructor instructor = new Instructor();
        instructor.setMember(member);
        instructor.setCourses(course);
        return instructorRepository.save(instructor);
    }

    public List<Course> getCourses(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EmailExistedException(email));
        if(member.getInstructor() == null) return null;
        List<Course> courses = member.getInstructor().getCourses();
        return courses;
    }

    @Transactional
    public void createCourse(Course course, String email) {
        /*
        여기서 email을 가지고 강의를 만들어야함,
        course에는 강의 정보인 강의 제목, 태그, 레벨, 카테고리, 가격, 추천 이들어감

         */

    }

}
