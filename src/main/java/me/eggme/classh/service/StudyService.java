package me.eggme.classh.service;

import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.CourseHistoryDTO;
import me.eggme.classh.domain.entity.*;
import me.eggme.classh.exception.NoSearchCourseClassException;
import me.eggme.classh.exception.NoSearchCourseException;
import me.eggme.classh.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudyService {

    @Autowired private CourseRepository courseRepository;
    @Autowired private CourseClassRepository courseClassRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private SignUpCourseRepository signUpCourseRepository;
    @Autowired private CourseHistoryRepository courseHistoryRepository;

    /**
     * 해당 유저가 해당 수업에 수강신청이 되었는지 체크 수강신청 완료 시  true, 수강신청이 안돼있으면 false
     * @param courseId 강의 pk
     * @param memberId 유저 pk
     * @return
     */
    @Transactional
    public boolean checkSignUpCourseMember(Long courseId, Long memberId){
        Course savedCourse = courseRepository.findById(courseId).orElseThrow(() ->
                new NoSearchCourseException());

        Member savedMember = memberRepository.findById(memberId).orElseThrow(() ->
                new UsernameNotFoundException("해당되는 유저를 찾을 수 없습니다."));

        SignUpCourse savedSignUpCourse = signUpCourseRepository.findByCourseAndMember(savedCourse, savedMember);
        if(savedSignUpCourse != null) return true;

        return false;
    }

    /**
     * 해당 수업에 대한 수업 기록을 조회하여 반환
     * @param memberId 유저 pk
     * @param classId 수업 pk
     * @return
     */
    @Transactional
    public CourseHistoryDTO getCourseHistory(Long memberId, Long classId) {
        Member savedMember = memberRepository.findById(memberId).orElseThrow(() ->
                new UsernameNotFoundException("해당되는 유저를 찾을 수 없습니다."));

        CourseClass savedCourseClass = courseClassRepository.findById(classId).orElseThrow(() ->
                new NoSearchCourseClassException());

        CourseHistory courseHistory = courseHistoryRepository.findByMemberAndCourseClass(savedMember, savedCourseClass);
        CourseHistoryDTO courseHistoryDTO = courseHistory.of();

        return courseHistoryDTO;
    }

    /**
     * 해당 강의에 대한 수업 기록들을 조회하여 반환
     * @param memberId 유저 pk
     * @param courseId 강의 pk
     * @return
     */
    @Transactional
    public List<CourseHistoryDTO> getCourseHistories(Long memberId, Long courseId) {
        Member savedMember = memberRepository.findById(memberId).orElseThrow(() ->
                new UsernameNotFoundException("해당되는 유저를 찾을 수 없습니다."));

        Course savedCourse = courseRepository.findById(courseId).orElseThrow(() ->
                new NoSearchCourseException());

        List<CourseHistory> courseHistories = courseHistoryRepository.findAllByMemberAndCourse(savedMember, savedCourse);
        List<CourseHistoryDTO> courseHistoryDTOS = courseHistories.stream().map(ch -> ch.of()).collect(Collectors.toList());

        return courseHistoryDTOS;
    }

    /***
     * 현재까지 들은 수업 데이터를 저장함
     * @param courseId 강의 pk
     * @param classId 수업 pk
     * @param memberId 유저 pk
     * @param currentTime 시간 데이터
     */
    @Transactional
    public void saveStudyData(Long courseId, Long classId, Long memberId, int currentTime) {
        Course savedCourse = courseRepository.findById(courseId).orElseThrow(() ->
                new NoSearchCourseException());

        Member savedMember = memberRepository.findById(memberId).orElseThrow(() ->
                new UsernameNotFoundException("해당되는 유저를 찾을 수 없습니다."));

        CourseClass savedCourseClass = courseClassRepository.findById(classId).orElseThrow(() ->
                new NoSearchCourseClassException());

        CourseHistory savedCourseHistory = courseHistoryRepository.findByMemberAndCourseClass(savedMember, savedCourseClass);
        if(savedCourseHistory == null){
            CourseHistory courseHistory = CourseHistory.builder()
                    .startTime(currentTime)
                    .endTime(savedCourseClass.getSeconds())
                    .build();

            CourseHistory savedNewCourseHistory = courseHistoryRepository.save(courseHistory);
            savedNewCourseHistory.setMember(savedMember);
            savedNewCourseHistory.setCourseClass(savedCourseClass);
            savedNewCourseHistory.setCourse(savedCourse);
            savedMember.addCourseHistory(savedNewCourseHistory);
        }else{
            if(savedCourseHistory.getStartTime() < currentTime){
                savedCourseHistory.setStartTime(currentTime);
            }
        }
    }

    /***
     * 만약 수업기록이 하나라도 있을 경우 false (해당 강의 기록이 있을 경우라고 이거 수정해야할듯)
     * @param memberId 유저 pk
     * @param courseId 강의 pk
     * @return
     */
    @Transactional
    public boolean hasCourseHistory(Long memberId, Long courseId) {
        Member savedMember = memberRepository.findById(memberId).orElseThrow(() ->
                new UsernameNotFoundException("해당되는 유저를 찾을 수 없습니다."));

        List<CourseHistory> courseHistories = savedMember.getCourseHistories().stream().filter(ch ->
                ch.getCourse().getId() == courseId).collect(Collectors.toList());

        if(courseHistories != null) return true;
        return false;
    }

    /**
     * 해당 수업 다음 수업을 조회
     * @param courseId 강의 pk
     * @param classId 수업 pk
     * @return
     */
    @Transactional
    public CourseClass nextCourseClass(Long courseId, Long classId) {

        Course savedCourse = courseRepository.findById(courseId).orElseThrow(() ->
                new NoSearchCourseException());

        Iterator<CourseSection> courseSections = savedCourse.getCourseSections().iterator();
        while(courseSections.hasNext()){
            CourseSection courseSection = courseSections.next();
            Iterator<CourseClass> courseClasses = courseSection.getCourseClasses().iterator();
            while(courseClasses.hasNext()){
                CourseClass courseClass = courseClasses.next();
                if(courseClass.getId() == classId){  // 해당 강의를 찾았을 때,
                    if(courseClasses.hasNext()){  // 해당 섹션에 다음 강의가 있다면
                        return courseClasses.next();
                    }else{
                        if(!courseSections.hasNext()){  // 만약 해당 섹션이 끝 섹션이라면 수업 최종 종료
                            return null;
                        }else{  // 만약 이게 섹션의 끝 강의라면 다음 섹션을 찾아서 강의를 찾음
                            CourseSection nextCourseSection = courseSections.next();
                            Iterator<CourseClass> findNextCourseClasses = nextCourseSection.getCourseClasses().iterator();
                            return findNextCourseClasses.next();
                        }
                    }
                }
            }
        }
        // 조건에 들어맞지 않은 강의는 존재하지 않는 강의
        return null;
    }

    /**
     * 만약 하나라도 수강기록이 있을 때 마지막 수강기록에 포함된 수업객체를 꺼내는 메서드
     * @param memberId 유저 pk
     * @param courseId 강의 pk
     * @return
     */
    @Transactional
    public CourseClass getLastCourseClass(Long memberId, Long courseId) {
        Course savedCourse = courseRepository.findById(courseId).orElseThrow(() ->
                new NoSearchCourseException());

        Member savedMember = memberRepository.findById(memberId).orElseThrow(() ->
                new UsernameNotFoundException("해당되는 유저를 찾을 수 없습니다."));
        // 해당 유저와 강의가 포함된 모든 강의를 찾은 다음
        List<CourseHistory> courseHistories = courseHistoryRepository.findAllByMemberAndCourse(savedMember, savedCourse);
        // 시작시간과 끝나는시간이 맞지않는 수업의 첫번재 객체를 필터링
        CourseHistory courseHistory = courseHistories.stream().filter(ch ->
                ch.getStartTime() != ch.getEndTime()).findFirst().get();

        CourseClass savedCourseClass = courseClassRepository.findById(courseHistory.getCourseClass().getId()).orElseThrow(() ->
                new NoSearchCourseClassException());

        return savedCourseClass;
    }
}
