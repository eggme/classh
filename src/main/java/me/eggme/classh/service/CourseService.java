package me.eggme.classh.service;

import lombok.extern.log4j.Log4j2;
import me.eggme.classh.dto.CourseCategory;
import me.eggme.classh.dto.CourseLevel;
import me.eggme.classh.entity.*;
import me.eggme.classh.exception.EmailExistedException;
import me.eggme.classh.exception.NoSearchCourseException;
import me.eggme.classh.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Log4j2
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private CourseSessionRepository courseSessionRepository;

    @Autowired
    private CourseClassRepository courseClassRepository;

    @Autowired
    private SignUpCourseRepository signUpCourseRepository;

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private TagRepository tagRepository;

    /***
     * 기본적인 강의 생성
     * 작업으로는 Course 클래스를 하나 만든 후 기본 URL을 등록, 그 후 기본 섹션과 수업을 넣은 후 강사 본인과의 관계를 맺음
     * 그 후 강사로 등록된 유저인지 아닌지 판단하여 데이터를 업데이트
     * @param courseName - 만들어질 강의의 이름
     * @param email - 강사의 이메일
     * @return
     */
    @Transactional
    public Course createCourseDefault(String courseName, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EmailExistedException(email));
        Course course = new Course(courseName);
        Course newCourse = courseRepository.save(course);
        newCourse.setUrl("temp_"+newCourse.getId());

        createDefaultSessionAndClass(newCourse);
        connectCourseUserRelation(newCourse, member);

        if(isRegisteredInstructor(member)){
            member.getInstructor().setCourses(newCourse);
        }else{
            Instructor instructor = saveInstructor(member, newCourse);
            member.setInstructor(instructor);
        }
        return newCourse;
    }

    /***
     * 해당 유저가 강사인지 아닌지 체크
     * @param member - 유저
     * @return
     */
    private boolean isRegisteredInstructor(Member member){
        Instructor instructor = instructorRepository.findByMember(member);
        if(instructor == null){
            return false;
        }
        return true;
    }

    /***
     * 해당 유저가 강사로 등록이 되어있지 않다면 강사 정보를 생성해서 반환
     * @param member - 어떤 유저가
     * @param course - 어떤 강의에 강사인가
     * @return
     */
    @Transactional
    protected Instructor saveInstructor(Member member, Course course) {
        Instructor instructor = new Instructor();
        instructor.setMember(member);
        instructor.setCourses(course);
        return instructorRepository.save(instructor);
    }

    /***
     * 해당 강사의 강의들을 모두 반환
     * @param email - 강사의 이메일
     * @return
     */
    public List<Course> getCourses(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EmailExistedException(email));
        if(member.getInstructor() == null) return null;
        List<Course> courses = member.getInstructor().getCourses();
        return courses;
    }

    /***
     * 일반 유저가 강의 URL을 클릭하여 강의를 조회하려할 때 호출
     * @param url - 해당 강의의 URL
     * @return
     */
    public Course getCourse(String url){
        Course course = courseRepository.findByUrl(url).orElseThrow(() -> new NoSearchCourseException());
        log.info(course);
        return course;
    }

    /***
     * 강사가 강의수정 클릭 시 ID를 기반으로 강의 정보 조회
     * @param id - 강의의 PK
     * @return
     */
    public Course getCourse(Long id){
        Course course = courseRepository.findById(id).orElseThrow(() -> new NoSearchCourseException());
        log.info(course);
        return course;
    }

    /***
     * 유저가 수강신청을 할 때 호출되는 메소드
     * @param course - 수강신청 할 강의
     * @param member - 유저
     */
    @Transactional
    protected void connectCourseUserRelation(Course course, Member member){
        SignUpCourse signUpCourse = new SignUpCourse();;
        SignUpCourse savedSignUpCourse = signUpCourseRepository.save(signUpCourse);
        member.connectCourse(course, savedSignUpCourse);
    }

    /***
     * 강의를 처음 만들었을 때, 강의의 섹션과 수업의 기본 값을 만들어서 DB에 저장
     * @param course - 섹션과 강의가 만들어질 강의
     */
    @Transactional
    protected void createDefaultSessionAndClass(Course course){
        CourseClass courseClass = new CourseClass();
        courseClass.setName("첫번째 수업을 만들어주세요");
        courseClass.setPublic(true);
        CourseClass savedCourseClass = courseClassRepository.save(courseClass);

        CourseSection courseSection = new CourseSection();
        courseSection.setName("첫번째 섹션의 제목을 입력해주세요.");
        courseSection.addCourseClass(savedCourseClass);
        CourseSection savedCourseSession = courseSessionRepository.save(courseSection);

        course.addCourseSession(savedCourseSession);
    }

    /***
     * 만든 강의의 정보를 수정, 강의 수정에서 데이터를 입력한 후 저장 및 다음이동 버튼을 누르면 실행
     * @param course 수정된 강의 데이터
     * @param courseCategory 강의 카테고리
     * @param courseLevel 강의 수준
     * @param recommendations 강의를 추천하는 사람
     * @param tags 강의의 스킬 태그
     * @return
     */
    @Transactional
    public Course editCourse(Course course, CourseCategory courseCategory, CourseLevel courseLevel,
                             List<Recommendation> recommendations, List<Tag> tags){
        Course findCourse = courseRepository.findById(course.getId()).orElseThrow(() -> new NoSearchCourseException());
        findCourse.setName(course.getName());
        findCourse.setPrice(course.getPrice());
        findCourse.setCourseCategory(courseCategory);
        findCourse.setCourseLevel(courseLevel);

        recommendations.stream().forEach(r -> findCourse.addCourseRecommendation(recommendationRepository.save(r)));
        tags.stream().forEach(t -> findCourse.addCourseTag(tagRepository.save(t)));

        return findCourse;
    }
}
