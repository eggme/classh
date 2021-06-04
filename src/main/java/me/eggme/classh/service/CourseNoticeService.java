package me.eggme.classh.service;

import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.CourseNoticeDTO;
import me.eggme.classh.domain.dto.NotificationEvent;
import me.eggme.classh.domain.dto.NotificationType;
import me.eggme.classh.domain.entity.*;
import me.eggme.classh.exception.NoSearchCourseException;
import me.eggme.classh.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseNoticeService {

    @Autowired
    private CourseNoticeRepository courseNoticeRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CourseCommentRepository courseCommentRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /***
     * 강사가 새소식을 등록함
     * @param courseNotice 새소식 정보
     * @param member 강사 정보
     * @param courseId 강의 pk
     * @return
     */
    @Transactional
    public String addNotice(CourseNotice courseNotice, Member member, Long courseId) {
        CourseNotice savedCourseNotice = courseNoticeRepository.save(courseNotice);
        Member savedMember = memberRepository.findById(member.getId()).orElseThrow(() ->
                new UsernameNotFoundException("해당 유저를 찾을 수 없습니다"));
        Course savedCourse = courseRepository.findById(courseId).orElseThrow(() ->
                new NoSearchCourseException());

        savedCourseNotice.setMember(savedMember);
        savedCourseNotice.setCourse(savedCourse);

        /* 추가사항 새소식 등록 시 알림 발송 */
        List<Member> memberList = savedCourse.getSignUpCourses().stream().map(suc ->
                suc.getMember()).collect(Collectors.toList());

        applicationEventPublisher.publishEvent(new NotificationEvent(memberList,
                savedMember.getNickName() + " 지식공유자님이 새소식을 등록했습니다.",
                    savedMember,
                    savedCourseNotice.getNotice(),
                    NotificationType.NEW_COURSE));

        return savedCourse.getUrl();
    }

    /***
     * 강의 pk를 받아서 해당된 새소식들을 전부 조회
     * @param courseId 강의 pk
     * @return
     */
    @Transactional
    public List<CourseNoticeDTO> getCourseNoticeList(Long courseId) {
        Course savedCourse = courseRepository.findById(courseId).orElseThrow(() ->
                new NoSearchCourseException());

        List<CourseNotice> list = courseNoticeRepository.findAllByCourse(savedCourse);
        List<CourseNoticeDTO> dtoList = list.stream().map(cn -> cn.of()).collect(Collectors.toList());
        return dtoList;
    }

    /***
     * 새소식을 수정할 때 새소식의 정보를 조회
     * @param id 새소식 pk
     * @return
     */
    @Transactional
    public CourseNotice selectNotice(Long id) {
        CourseNotice savedCourseNotice = courseNoticeRepository.findById(id).orElseThrow(() ->
                new RuntimeException("해당되는 공지사항이 없습니다"));
        return savedCourseNotice;
    }

    /***
     * 강사가 새소식을 수정한 후 강의 url을 전송
     * @param courseNotice 수정할 새소식 데이터
     * @return
     */
    @Transactional
    public String editNotice(CourseNotice courseNotice) {
        CourseNotice savedCourseNotice = courseNoticeRepository.findById(courseNotice.getId()).orElseThrow(() ->
                new RuntimeException("해당되는 공지사항이 없습니다"));
        savedCourseNotice.setTitle(courseNotice.getTitle());
        savedCourseNotice.setNotice(courseNotice.getNotice());
        savedCourseNotice.setPublic(courseNotice.isPublic());

        String url = savedCourseNotice.getCourse().getUrl();

        return url;
    }

    /***
     * 새소식을 삭제할 때 호출
     * @param id 삭제할 새소식 pk
     * @return
     */
    @Transactional
    public String deleteNotice(Long id) {
        CourseNotice savedCourseNotice = courseNoticeRepository.findById(id).orElseThrow(() ->
                new RuntimeException("해당되는 공지사항이 없습니다"));
        String url = savedCourseNotice.getCourse().getUrl();
        savedCourseNotice.getCourse().getCourseNotices().remove(savedCourseNotice);
        savedCourseNotice.getCourseComments().stream().forEach(cc -> cc.setCourseNotice(null));
        courseNoticeRepository.delete(savedCourseNotice);
        return url;
    }

    /***
     * 새소식에 댓글을 추가함
     * @param id 댓글 pk
     * @param member 댓글을 다는 사람
     * @param content 댓글 내용
     * return 강의 url
     */
    @Transactional
    public String addCommentForNotice(Long id, Member member, String content) {
        CourseNotice savedCourseNotice = courseNoticeRepository.findById(id).orElseThrow(() ->
                new RuntimeException("해당되는 공지사항이 없습니다"));
        Member savedMember = memberRepository.findById(member.getId()).orElseThrow(() ->
                new UsernameNotFoundException("해당되는 유저가 없습니다"));

        CourseComment courseComment = CourseComment.builder()
                .commentContent(content)
                .build();

        CourseComment savedCourseComment = courseCommentRepository.save(courseComment);
        savedCourseComment.setMember(savedMember);
        savedCourseComment.setCourseNotice(savedCourseNotice);

        String url = savedCourseNotice.getCourse().getUrl();
        return url;
    }

    /***
     * 새소식의 댓글을 삭제할 때 호출
     * @param id 삭제할 댓글의 pk
     */
    @Transactional
    public void deleteCommentForNotice(Long id) {
        CourseComment savedCourseComment = courseCommentRepository.findById(id).orElseThrow(() ->
                new RuntimeException("해당되는 댓글이 없습니다"));
        savedCourseComment.getCourseNotice().getCourseComments().remove(savedCourseComment);
        courseCommentRepository.delete(savedCourseComment);
    }

    /***
     * 새소식의 댓글을 변경할 떄 호출
     * @param id 변경될 댓글의 pk
     * @param content 변경될 댓글의 내용
     * @return
     */
    @Transactional
    public CourseComment editCommentForNotice(Long id, String content) {
        CourseComment savedCourseComment = courseCommentRepository.findById(id).orElseThrow(() ->
                new RuntimeException("해당되는 댓글이 없습니다."));
        savedCourseComment.setCommentContent(content);
        return savedCourseComment;
    }
}
