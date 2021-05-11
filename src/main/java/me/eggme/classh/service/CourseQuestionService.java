package me.eggme.classh.service;

import me.eggme.classh.domain.entity.Course;
import me.eggme.classh.domain.entity.CourseQuestion;
import me.eggme.classh.domain.entity.CourseTag;
import me.eggme.classh.domain.entity.Member;
import me.eggme.classh.exception.NoSearchCourseException;
import me.eggme.classh.repository.CourseQuestionRepository;
import me.eggme.classh.repository.CourseRepository;
import me.eggme.classh.repository.CourseTagRepository;
import me.eggme.classh.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseQuestionService {

    @Autowired private CourseQuestionRepository courseQuestionRepository;
    @Autowired private CourseRepository courseRepository;
    @Autowired private CourseTagRepository courseTagRepository;
    @Autowired private MemberRepository memberRepository;

    /***
     * 사용자가 질문게시판에 질문을 올림
     * @param courseQuestion 질문 정보
     * @param member 질문을 올린 사용자
     * @param course_id 강의 번호
     * @return
     */
    @Transactional
    public CourseQuestion saveCourseQuestion(CourseQuestion courseQuestion, Member member, Long course_id) {
        Member savedMember = memberRepository.findById(member.getId()).orElseThrow(() ->
                new AuthenticationCredentialsNotFoundException("해당되는 유저를 찾을 수 없습니다."));

        CourseQuestion savedCourseQuestion = courseQuestionRepository.save(courseQuestion);
        Course savedCourse = courseRepository.findById(course_id).orElseThrow(() -> new NoSearchCourseException());

        savedCourseQuestion.setMember(savedMember);
        savedCourseQuestion.setCourse(savedCourse);
        List<CourseTag> savedCourseTags = savedCourseQuestion.getCourseTags().stream().map(ct -> {
            CourseTag savedCourseTag = courseTagRepository.save(ct);
            savedCourseTag.setCourse(savedCourse);
            savedCourseTag.setCourseQuestion(savedCourseQuestion);
            return savedCourseTag;
        }).collect(Collectors.toList());
        savedCourseQuestion.setCourseTags(savedCourseTags);
        return savedCourseQuestion;
    }

    @Transactional
    public List<CourseQuestion> selectCourseQuestions(Long course_id) {
        Course savedCourse = courseRepository.findById(course_id).orElseThrow(() -> new NoSearchCourseException());
        return savedCourse.getCourseQuestions();
    }

    @Transactional
    public CourseQuestion getCourseQuestion(Long id) {
        CourseQuestion savedCourseQuestion = courseQuestionRepository.findById(id).orElseThrow(()
                -> new RuntimeException());
        return savedCourseQuestion;
    }
}
