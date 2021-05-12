package me.eggme.classh.service;

import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.CourseCommentDTO;
import me.eggme.classh.domain.dto.QuestionStatus;
import me.eggme.classh.domain.entity.*;
import me.eggme.classh.exception.NoSearchCourseException;
import me.eggme.classh.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseQuestionService {

    @Autowired private CourseQuestionRepository courseQuestionRepository;
    @Autowired private CourseRepository courseRepository;
    @Autowired private CourseTagRepository courseTagRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private CourseCommentRepository courseCommentRepository;

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

    /***
     * 질문&답변에 답변을 저장하는 메소드
     * @param question_id 질문 pk
     * @param member 답변을 저장하려고하는 유저 객체
     * @param commentContent 답변
     */
    @Transactional
    public void addCourseComment(Long question_id, Member member, String commentContent) {
        CourseComment courseComment = CourseComment.builder()
                                .commentContent(commentContent)
                                .build();
        CourseComment savedCourseComment = courseCommentRepository.save(courseComment);
        Member savedMember = memberRepository.findById(member.getId()).orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다"));
        CourseQuestion savedCourseQuestion = courseQuestionRepository.findById(question_id).orElseThrow(() -> new RuntimeException());

        savedCourseComment.setMember(savedMember);
        savedCourseComment.setCourseQuestion(savedCourseQuestion);
    }

    /***
     * 질문에 대한 답변들을 조회
     * @param savedCourseQuestion
     * @return
     */
    @Transactional
    public List<CourseCommentDTO> selectCourseComment(CourseQuestion savedCourseQuestion) {
        List<CourseComment> commentList = courseCommentRepository.findByCourseQuestion(savedCourseQuestion);
        List<CourseCommentDTO> commentDTOList = commentList.stream().map(cc -> cc.of()).collect(Collectors.toList());
        return commentDTOList;
    }

    /***
     * 질문을 올린 사람이 질문을 수정할 때 호출
     * @param courseQuestion 수정한 질문
     * @return
     */
    @Transactional
    public CourseQuestion editCourseQuestion(CourseQuestion courseQuestion) {
        CourseQuestion savedCourseQuestion = courseQuestionRepository.findById(courseQuestion.getId()).orElseThrow(() -> new RuntimeException());
        savedCourseQuestion.setTitle(courseQuestion.getTitle());

        Course savedCourse = savedCourseQuestion.getCourse();

        savedCourseQuestion.getCourseTags().clear();

        List<CourseTag> savedCourseTagList = courseQuestion.getCourseTags().stream().map(ct -> {
            CourseTag savedCourseTag = courseTagRepository.save(ct);
            savedCourseTag.setCourse(savedCourse);
            savedCourseTag.setCourseQuestion(savedCourseQuestion);
            return savedCourseTag;
        }).collect(Collectors.toList());

        savedCourseQuestion.getCourseTags().addAll(savedCourseTagList);

        savedCourseQuestion.setContent(courseQuestion.getContent());
        return savedCourseQuestion;
    }

    /***
     * 질문이 상태를 반전시킴 해결 -> 미해결, 미해결 -> 해결
     * @param id 반전시킬 질문 pk
     */
    @Transactional
    public void changeCourseQuestionStatus(Long id, String status) {
        QuestionStatus questionStatus = QuestionStatus.findByValue(status);
        CourseQuestion savedCourseQuestion = courseQuestionRepository.findById(id).orElseThrow(() -> new RuntimeException());
        savedCourseQuestion.setQuestionStatus(questionStatus);
    }

    /***
     * 질문을 삭제하고 삭제한 질문의 강의 url을 구해서 질문리스트페이지로 이동
     * @param id 삭제할 질문의 pk
     * @return
     */
    @Transactional
    public String deleteCourseQuestion(Long id) {
        String resultUrl = "";
        CourseQuestion savedCourseQuestion = courseQuestionRepository.findById(id).orElseThrow(() -> new RuntimeException());
        resultUrl = savedCourseQuestion.getCourse().getUrl();
        courseQuestionRepository.delete(savedCourseQuestion);
        return resultUrl;
    }
}
