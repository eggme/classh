package me.eggme.classh.service;

import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.CourseNoteDTO;
import me.eggme.classh.domain.entity.Course;
import me.eggme.classh.domain.entity.CourseClass;
import me.eggme.classh.domain.entity.CourseNote;
import me.eggme.classh.domain.entity.Member;
import me.eggme.classh.exception.NoSearchCourseClassException;
import me.eggme.classh.exception.NoSearchCourseException;
import me.eggme.classh.repository.CourseClassRepository;
import me.eggme.classh.repository.CourseNoteRepository;
import me.eggme.classh.repository.CourseRepository;
import me.eggme.classh.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class CourseNoteService {

    @Autowired
    private CourseNoteRepository courseNoteRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseClassRepository courseClassRepository;

    /**
     * 유저가 강의안에 있는 수업에 노트를 입력함
     * @param member 유저 객체
     * @param courseId 강의 pk
     * @param courseClass_id 강의 수업 pk
     * @param courseNote 노트 데이터
     * @return
     */
    @Transactional
    public CourseNoteDTO saveCourseNote(Member member, Long courseId, Long courseClass_id,
                                        CourseNote courseNote){
        Member savedMember = memberRepository.findById(member.getId()).orElseThrow(() ->
                new UsernameNotFoundException("해당되는 유저가 없습니다."));

        Course savedCourse = courseRepository.findById(courseId).orElseThrow(() ->
                new NoSearchCourseException());

        CourseClass savedCourseClass = courseClassRepository.findById(courseClass_id).orElseThrow(() ->
                new NoSearchCourseClassException());

        CourseNote savedCourseNote = courseNoteRepository.save(courseNote);

        savedCourseNote.setMember(savedMember);
        savedCourseNote.setCourse(savedCourse);
        savedCourseNote.setCourseClass(savedCourseClass);

        savedMember.addCourseNote(savedCourseNote);
        return savedCourseNote.of();
    }

    /**
     * 유저가 특정 노트의 정보를 수정
     * @param note_id 노트 pk
     * @param courseNote 수정될 노트의 정보
     * @return
     */
    @Transactional
    public CourseNoteDTO editCourseNote(Long note_id, CourseNote courseNote){

        CourseNote savedCourseNote = courseNoteRepository.findById(note_id).orElseThrow(() ->
                new RuntimeException("해당되는 노트가 없습니다."));

        savedCourseNote.setContent(courseNote.getContent());
        savedCourseNote.setSeconds(courseNote.getSeconds());
        return savedCourseNote.of();
    }

    /**
     * 해당된 수업의 사용자 노트들을 조히
     * @param member 사용자
     * @param courseClass_id 해당된 수업 pk
     * @return
     */
    @Transactional
    public List<CourseNote> getNotes(Member member, Long courseClass_id){
        Member savedMember = memberRepository.findById(member.getId()).orElseThrow(() ->
                new UsernameNotFoundException("해당되는 유저가 없습니다."));

        CourseClass savedCourseClass = courseClassRepository.findById(courseClass_id).orElseThrow(() ->
                new NoSearchCourseClassException());

        List<CourseNote> savedCourseNotes = courseNoteRepository.findAllByMemberAndCourseClassOrderByIdAsc(savedMember, savedCourseClass);

        log.info("note size = " + savedCourseNotes.size());
        return savedCourseNotes;
    }
}
