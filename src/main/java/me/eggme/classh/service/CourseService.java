package me.eggme.classh.service;

import lombok.extern.log4j.Log4j2;
import me.eggme.classh.entity.Course;
import me.eggme.classh.entity.Instructor;
import me.eggme.classh.entity.Member;
import me.eggme.classh.exception.EmailExistedException;
import me.eggme.classh.exception.NoSearchCourseException;
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

    // 강의 기본 생성 (이름과 주소만 설정)
    @Transactional
    public Course createCourseDefault(String courseName, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EmailExistedException(email));
        Course course = new Course(courseName);
        Course newCourse = courseRepository.save(course);
        newCourse.setUrl("temp_"+newCourse.getId());
        if(isRegisteredInstructor(member)){
            member.getInstructor().setCourses(newCourse);
        }else{
            Instructor instructor = saveInstructor(member, newCourse);
            member.setInstructor(instructor);
        }
        return newCourse;
    }

    // 강사인지 체크
    private boolean isRegisteredInstructor(Member member){
        Instructor instructor = instructorRepository.findByMember(member);
        if(instructor == null){
            return false;
        }
        return true;
    }

    // 유저가 강사가 아니면 강사정보 저장
    @Transactional
    protected Instructor saveInstructor(Member member, Course course) {
        Instructor instructor = new Instructor();
        instructor.setMember(member);
        instructor.setCourses(course);
        return instructorRepository.save(instructor);
    }

    // 강사의 강의들을 모두 리턴
    public List<Course> getCourses(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EmailExistedException(email));
        if(member.getInstructor() == null) return null;
        List<Course> courses = member.getInstructor().getCourses();
        return courses;
    }

    // 신분에 관계없이 강의 리턴, URL 기준
    public Course getCourse(String url){
        Course course = courseRepository.findByUrl(url).orElseThrow(() -> new NoSearchCourseException());
        log.info(course);
        return course;
    }

    @Transactional
    public void createCourse(Course course, String email) {
        /*
        여기서 email을 가지고 강의를 만들어야함,
        course에는 강의 정보인 강의 제목, 태그, 레벨, 카테고리, 가격, 추천 이들어감

         */

    }

}
