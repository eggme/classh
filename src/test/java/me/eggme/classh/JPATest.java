package me.eggme.classh;

import me.eggme.classh.dto.MemberDTO;
import me.eggme.classh.entity.Course;
import me.eggme.classh.entity.Instructor;
import me.eggme.classh.entity.Member;
import me.eggme.classh.repository.CourseRepository;
import me.eggme.classh.repository.InstructorRepository;
import me.eggme.classh.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JPATest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    @Test
    public void jpaInsertTest(){
        Member member = new Member();
        member.setName("승준");
        member.setEmail("kyyeto9984@naver.com");
        member.setPassword("123123");
        Member newMember = memberRepository.save(member);


        Course course = new Course();
        course.setName("테스트 강의");
        courseRepository.save(course);

        Course course1 = new Course();
        course1.setName("테스트 1");
        courseRepository.save(course1);

        Instructor instructor = new Instructor();
        instructor.setMember(newMember);
        instructor.setCourses(course);
        newMember.setInstructor(instructor);
        Instructor save = instructorRepository.save(instructor);


        newMember.getInstructor().getCourses().add(course1);

        System.out.println(newMember);

        MemberDTO dto = modelMapper.map(newMember, MemberDTO.class);
        System.out.println(dto);

        System.out.println(course.getInstructor().getMember().getName());
        save.getCourses().stream().forEach(c -> System.out.println(c.getName()));
        newMember.getInstructor().getCourses().stream().forEach(c -> System.out.println(newMember.getName() + " : " + c.getName()));

    }

}
