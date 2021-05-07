package me.eggme.classh.service;

import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.CourseCategory;
import me.eggme.classh.domain.dto.CourseClassDTO;
import me.eggme.classh.domain.dto.CourseLevel;
import me.eggme.classh.domain.dto.CourseState;
import me.eggme.classh.domain.entity.*;
import me.eggme.classh.exception.EmailExistedException;
import me.eggme.classh.exception.NoSearchCourseClassException;
import me.eggme.classh.exception.NoSearchCourseException;
import me.eggme.classh.exception.NoSearchCourseSectionException;
import me.eggme.classh.repository.*;
import me.eggme.classh.utils.FileUploadFactory;
import me.eggme.classh.utils.FileUploader;
import me.eggme.classh.utils.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;

@Service
@Slf4j
public class CourseService {

    @Autowired private CourseRepository courseRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private InstructorRepository instructorRepository;
    @Autowired private CourseSessionRepository courseSessionRepository;
    @Autowired private CourseClassRepository courseClassRepository;
    @Autowired private SignUpCourseRepository signUpCourseRepository;
    @Autowired private RecommendationRepository recommendationRepository;
    @Autowired private SkillTagRepository skillTagRepository;

    private FileUploader fileUploader;

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
        Member member = memberRepository.findByUsername(email).orElseThrow(() -> new EmailExistedException(email));
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
        Member member = memberRepository.findByUsername(email).orElseThrow(() -> new EmailExistedException(email));
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
        return course;
    }

    /***
     * 강사가 강의수정 클릭 시 ID를 기반으로 강의 정보 조회
     * @param id - 강의의 PK
     * @return
     */
    public Course getCourse(Long id){
        Course course = courseRepository.findById(id).orElseThrow(() -> new NoSearchCourseException());
        return course;
    }

    /***
     * 강의 수업 편집 시 요청 데이터 조회
     * @param id
     * @return
     */
    public CourseClass getCourseClass(Long id){
        CourseClass courseClass = courseClassRepository.findById(id).orElseThrow(() -> new NoSearchCourseException());
        return courseClass;
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
        courseClass.setPreview(true);
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
     * @param skillTags 강의의 스킬 태그
     * @return
     */
    @Transactional
    public Course editCourse(Course course, CourseCategory courseCategory, CourseLevel courseLevel,
                             List<Recommendation> recommendations, List<SkillTag> skillTags){
        Course findCourse = courseRepository.findById(course.getId()).orElseThrow(() -> new NoSearchCourseException());
        findCourse.setName(course.getName());
        findCourse.setPrice(course.getPrice());
        findCourse.setCourseCategory(courseCategory);
        findCourse.setCourseLevel(courseLevel);
        if(recommendations != null){
            if(!hasRecommendations(findCourse)) recommendations.stream().forEach(r-> findCourse.addCourseRecommendation(recommendationRepository.save(r)));
            else changeRecommendations(findCourse, recommendations);
        }
        if(skillTags != null){
            if(!hasSkillTag(findCourse)) skillTags.stream().forEach(st -> findCourse.addSkillTag(skillTagRepository.save(st)));
            else changeSkillTags(findCourse, skillTags);
        }
        return findCourse;
    }

    /***
     * 강의의 짧은 글 소개왜 긴 글 소개 변경
     * @param course
     * @return
     */
    @Transactional
    public Course editCourse(Course course){
        Course findCourse = courseRepository.findById(course.getId()).orElseThrow(() -> new NoSearchCourseException());
        findCourse.setShortDesc(course.getShortDesc());
        findCourse.setLongDesc(course.getLongDesc());
        return findCourse;
    }

    /***
     * description 페이지에서 강사가 긴 글 소개에 이미지를 업로드 시켰을 때 이미지 업로딩
     * @param file
     * @return
     */
    @Transactional
    public String uploadCourseDescriptionImage(File file){
        fileUploader = FileUploadFactory.getFileUploader(ResourceType.IMAGE);
        return fileUploader.saveFile(file, ResourceType.IMAGE);
    }

    /***
     *  강사가 섹션을 추가 했을 때
     * @param course
     * @return
     */
    @Transactional
    public CourseSection createSection(Course course, CourseSection courseSection) {
        Course findCourse = courseRepository.findById(course.getId()).orElseThrow(() -> new NoSearchCourseException());
        CourseSection savedCourseSection = courseSessionRepository.save(courseSection);
        findCourse.addCourseSession(savedCourseSection);
        return savedCourseSection;
    }

    /***
     * 강사가 섹션을 수정했을 때
     */
    @Transactional
    public CourseSection editSection(CourseSection courseSection){
        CourseSection savedCourseSection =
                courseSessionRepository.findById(courseSection.getId()).orElseThrow(
                        () -> new NoSearchCourseSectionException());
        savedCourseSection.setName(courseSection.getName());
        savedCourseSection.setGoal(courseSection.getGoal());
        return savedCourseSection;
    }

    /***
     * 강사가 수업을 추가 했을 때
     * @param courseClass
     * @return
     */
    @Transactional
    public CourseClass createClass(CourseClass courseClass, Long id) {
        CourseClass savedClass = courseClassRepository.save(courseClass);

        CourseSection courseSection = courseSessionRepository.findById(id).orElseThrow(() -> new NoSearchCourseSectionException());
        courseSection.addCourseClass(savedClass);
        return savedClass;
    }

    /***
     * 강사가 강의를 올리면 변환한 뒤 DB에 저장
     * @param class_id
     * @param file
     * @param duration
     * @return
     */
    @Transactional
    public CourseClassDTO saveCourseVideo(Long class_id, File file, double duration) {
        CourseClass findClass = courseClassRepository.findById(class_id).orElseThrow(() -> new NoSearchCourseClassException());
        fileUploader = FileUploadFactory.getFileUploader(ResourceType.VIDEO);
        String videoUrl = fileUploader.saveFile(file, ResourceType.VIDEO);
        findClass.setMediaPath(videoUrl);
        findClass.setSeconds((int)duration);
        return findClass.of();
    }

    /***
     * 강사가 강의 자료를 올리면 변환한 뒤 DB 저장
     * @param class_id
     * @param file
     * @return
     */
    @Transactional
    public CourseClassDTO saveCourseStudyFile(Long class_id, File file) {
        fileUploader = FileUploadFactory.getFileUploader(ResourceType.PDF);
        String studyFileURL = fileUploader.saveFile(file, ResourceType.PDF);
        CourseClass findClass = courseClassRepository.findById(class_id).orElseThrow(() -> new NoSearchCourseClassException());
        findClass.setDataPath(studyFileURL);
        return findClass.of();
    }

    /***
     * 수강생이 강의의 수강평을 올림
     * @param course_id
     * @param rate
     * @param reviewContent
     */
    @Transactional
    public Course saveCourseReview(Long course_id, String username, int rate, String reviewContent) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new EmailExistedException(username));
        Course findCourse = courseRepository.findById(course_id).orElseThrow(() -> new NoSearchCourseException());
        CourseReview courseReview = new CourseReview();
        courseReview.setRate(rate);
        courseReview.setReviewContent(reviewContent);
        findCourse.addCourseReview(courseReview);
        member.addCourseReview(courseReview);
        return findCourse;
    }

    /***
     * 강사가 수업을 편집하고 최종 저장할 때 호출
     * @param courseClass
     */
    @Transactional
    public Course editCourseClass(CourseClass courseClass) {
        CourseClass findClass = courseClassRepository.findById(courseClass.getId()).orElseThrow(() -> new NoSearchCourseException());
        findClass.setDataPath(courseClass.getDataPath());
        findClass.setName(courseClass.getName());
        findClass.setInstructorMemo(courseClass.getInstructorMemo());
        findClass.setMediaPath(courseClass.getMediaPath());
        findClass.setPreview(courseClass.isPreview());

        Long id = findClass.getCourseSection().getCourse().getId();
        return courseRepository.findById(id).orElseThrow(() -> new NoSearchCourseException());
    }

    /***
     * 강의 썸네일 교체
     * @param id
     * @param file
     */
    @Transactional
    public Course saveCourseThumbnail(Long id, File file) {
        fileUploader = FileUploadFactory.getFileUploader(ResourceType.IMAGE);
        String thumbnailPath = fileUploader.saveFile(file, ResourceType.IMAGE);
        Course findCourse = courseRepository.findById(id).orElseThrow(() -> new NoSearchCourseException());
        findCourse.setCourseImg(thumbnailPath);
        return findCourse;
    }

    private boolean hasRecommendations(Course course){
        return course.getRecommendations().size() == 0 ? false : true;
    }

    private boolean hasSkillTag(Course course){
        return course.getSkillTags().size() == 0 ? false : true;
    }

    @Transactional
    protected void changeSkillTags(Course course, List<SkillTag> skillTags){
        skillTags.stream().forEach(st-> {
            st.setCourse(course);
            skillTagRepository.save(st);
        });
        course.getSkillTags().clear();
        course.getSkillTags().addAll(skillTags);
    }

    @Transactional
    protected void changeRecommendations(Course course, List<Recommendation> recommendations){
        recommendations.stream().forEach(r-> {
            r.setCourse(course);
            recommendationRepository.save(r);
        });
        course.getRecommendations().clear();
        course.getRecommendations().addAll(recommendations);
    }

    public boolean isPreviewCourseClass(CourseClass courseClass) {
        return courseClass.isPreview();
    }

    /***
     * 해당하는 섹션을 삭제
     * @param id 식별자
     */
    @Transactional
    public void deleteCourseSection(Long id) {
        CourseSection savedCourseSection
                = courseSessionRepository.findById(id).orElseThrow(
                () -> new NoSearchCourseSectionException());
        courseSessionRepository.delete(savedCourseSection);
    }

    /***
     * 해당하는 수업을 삭제
     * @param id 식별자
     */
    @Transactional
    public void deleteCourseClass(Long id) {
        CourseClass savedCourseClass
                = courseClassRepository.findById(id).orElseThrow(
                () -> new NoSearchCourseClassException()
        );
        courseClassRepository.deleteById(id);
    }

    /***
     * 강사가 최종적으로 제출 버튼을 눌러 임시저장 상태에서 제출 상태로 변경
     * @param id
     */
    @Transactional
    public void submitted(Long id) {
        Course savedCourse = courseRepository.findById(id).orElseThrow(() -> new NoSearchCourseException());
        savedCourse.setCourseState(CourseState.SUBMIT);
    }
}
