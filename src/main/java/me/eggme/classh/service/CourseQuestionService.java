package me.eggme.classh.service;

import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.CourseCommentDTO;
import me.eggme.classh.domain.dto.NotificationEvent;
import me.eggme.classh.domain.dto.NotificationType;
import me.eggme.classh.domain.dto.QuestionStatus;
import me.eggme.classh.domain.entity.*;
import me.eggme.classh.exception.NoSearchCourseException;
import me.eggme.classh.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseQuestionService {

    @Autowired private CourseQuestionRepository courseQuestionRepository;
    @Autowired private CourseRepository courseRepository;
    @Autowired private CourseTagRepository courseTagRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private CourseCommentRepository courseCommentRepository;
    @Autowired private ApplicationEventPublisher applicationEventPublisher;

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
    public Set<CourseQuestion> selectCourseQuestions(Long course_id) {
        Course savedCourse = courseRepository.findById(course_id).orElseThrow(() -> new NoSearchCourseException());
        return savedCourse.getCourseQuestions();
    }

    @Transactional
    public CourseQuestion getCourseQuestion(Long id) {
        CourseQuestion savedCourseQuestion = courseQuestionRepository.findByIdAndComments(id).orElse(
           courseQuestionRepository.findById(id).orElseThrow(() -> new RuntimeException()));
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

        /* 답변 등록 시 알림 발송 */
        Long target = savedCourseQuestion.getId();

        applicationEventPublisher.publishEvent(new NotificationEvent(Arrays.asList(savedMember),
                savedCourseQuestion.getMember().getNickName() + "의 질문 ["+ savedCourseQuestion.getTitle() +"]에 답변이 달렸습니다.",
                savedMember,
                commentContent,
                target,
                NotificationType.NEW_COURSE));
    }

    /***
     * 질문에 대한 답변들을 조회
     * @param savedCourseQuestion
     * @return
     */
    @Transactional
    public Set<CourseCommentDTO> selectCourseComment(CourseQuestion savedCourseQuestion) {
        Set<CourseComment> commentSet = courseCommentRepository.findByCourseQuestion(savedCourseQuestion);
        Set<CourseCommentDTO> commentDTOList = commentSet.stream().map(cc -> cc.of()).collect(Collectors.toSet());
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

        Set<CourseTag> savedCourseTagList = courseQuestion.getCourseTags().stream().map(ct -> {
            CourseTag savedCourseTag = courseTagRepository.save(ct);
            savedCourseTag.setCourse(savedCourse);
            savedCourseTag.setCourseQuestion(savedCourseQuestion);
            return savedCourseTag;
        }).collect(Collectors.toSet());

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
    public void deleteCourseQuestion(Long id) {
        CourseQuestion savedCourseQuestion = courseQuestionRepository.findById(id).orElseThrow(() -> new RuntimeException());
        savedCourseQuestion.deleteCourseQuestion();
        courseQuestionRepository.delete(savedCourseQuestion);
    }

    /***
     * 질문답변 게시판의 질문에 대한 답변의 댓글을 추가할 때 실행
     * @param id 댓글이 달릴 답변 pk
     * @param member 댓글을 작성하는 사람
     * @param content 댓글 내용
     */
    @Transactional
    public void addReply(Long id, Member member, String content) {
        CourseComment savedCourseComment = courseCommentRepository.findById(id).orElseThrow(() ->
                new RuntimeException());
        Member savedMember = memberRepository.findById(member.getId()).orElseThrow(() ->
                new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));

        CourseComment childCourseComment = CourseComment.builder()
                                .commentContent(content)
                                .build();
        CourseComment savedChildCourseComment = courseCommentRepository.save(childCourseComment);
        savedChildCourseComment.setMember(savedMember);
        savedChildCourseComment.setParent(savedCourseComment);

        savedCourseComment.getChildren().add(savedChildCourseComment);
    }

    /***
     * 답변이나 댓글을 삭제할때 호출
     * @param id 삭제할 댓글/답글 pk
     */
    @Transactional
    public Long deleteCourseComment(Long id) {
        CourseComment savedCourseComment = courseCommentRepository.findById(id).orElseThrow(() -> new RuntimeException("답글이 존재하지 않습니다"));
        Long redirectId = 0L;
        if( savedCourseComment.getCourseQuestion() == null ) {
            redirectId = savedCourseComment.getParent().getCourseQuestion().getId();
            savedCourseComment.getParent().getChildren().remove(savedCourseComment);
        }else {
            redirectId = savedCourseComment.getCourseQuestion().getId();
            savedCourseComment.getCourseQuestion().getCourseComments().remove(savedCourseComment);
        }

        courseCommentRepository.delete(savedCourseComment);
        return redirectId;
    }

    /***
     * id를 기반으로 답변/댓글을 검색 후 반환
     * @param id 답변/댓글 pk
     * @return
     */
    @Transactional
    public CourseComment getCourseComment(Long id) {
        CourseComment savedCourseComment = courseCommentRepository.findById(id).orElseThrow(() ->
                new RuntimeException("해당되는 댓글이 없습니다."));
        return savedCourseComment;
    }

    /***
     * id를 기반으로 답변/댓글을 수정하고 질문 id를 반환
     * @param id 답변/댓글 pk
     * @param content 수정될 답변/댓글 내용
     */
    @Transactional
    public Long editCourseComment(Long id, String content) {
        CourseComment savedCourseComment = courseCommentRepository.findById(id).orElseThrow(() ->
                new RuntimeException("해당되는 댓글이 없습니다."));
        savedCourseComment.setCommentContent(content);

        Long redirect_id = 0L;
        if(savedCourseComment.getCourseQuestion() != null)
            redirect_id = savedCourseComment.getCourseQuestion().getId();
        else
            redirect_id = savedCourseComment.getParent().getCourseQuestion().getId();

        return redirect_id;
    }

}
