package me.eggme.classh.service;

import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.entity.Course;
import me.eggme.classh.domain.entity.CourseNotice;
import me.eggme.classh.domain.entity.Member;
import me.eggme.classh.exception.NoSearchCourseException;
import me.eggme.classh.repository.CourseNoticeRepository;
import me.eggme.classh.repository.CourseRepository;
import me.eggme.classh.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class CourseNoticeService {

    @Autowired
    private CourseNoticeRepository courseNoticeRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private MemberRepository memberRepository;

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

        return savedCourse.getUrl();
    }

    /***
     * 강의 pk를 받아서 해당된 새소식들을 전부 조회
     * @param courseId 강의 pk
     * @return
     */
    @Transactional
    public List<CourseNotice> getCourseNoticeList(Long courseId) {
        Course savedCourse = courseRepository.findById(courseId).orElseThrow(() ->
                new NoSearchCourseException());

        List<CourseNotice> list = courseNoticeRepository.findAllByCourse(savedCourse);
        return list;
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
        savedCourseNotice.getCourseComments().stream().forEach(cc-> cc.setCourseNotice(null));
        courseNoticeRepository.delete(savedCourseNotice);
        return url;
    }
}
