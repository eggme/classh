package me.eggme.classh.service;

import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.*;
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
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseService {

    @Autowired private CourseRepository courseRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private CourseSessionRepository courseSessionRepository;
    @Autowired private CourseClassRepository courseClassRepository;
    @Autowired private SignUpCourseRepository signUpCourseRepository;
    @Autowired private RecommendationRepository recommendationRepository;
    @Autowired private SkillTagRepository skillTagRepository;
    @Autowired private PaymentRepository paymentRepository;
    @Autowired private CartRepository cartRepository;

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
        newCourse.setUrl("temp_" + newCourse.getId());

        createDefaultSessionAndClass(newCourse);
        connectCourseUserRelation(newCourse, member);
        member.getInstructor().setCourses(newCourse);

        return newCourse;
    }

    /***
     * 해당 강사의 강의들을 모두 반환
     * @param username - 강사의 이메일
     * @return
     */
    public List<Course> getCourses(String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new EmailExistedException(username));
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
    public void createDefaultSessionAndClass(Course course){
        CourseClass courseClass = new CourseClass();
        courseClass.setName("첫번째 수업을 만들어주세요");
        courseClass.setPreview(true);
        /* 테스트용 반드시 제거할 것  시작 */
        courseClass.setMediaPath("https://res.cloudinary.com/dg8tebwjm/video/upload/v1622075948/78030bfba74f5bfd625ecb75755f3b8073f351ab4a6bce15438f7d157e93e891.mp4");
        /* 테스트용 반드시 제거할 것 끝 */
        CourseClass savedCourseClass = courseClassRepository.save(courseClass);

        /* 테스트용 반드시 제거할 것 시작 */
        CourseClass courseClass2 = new CourseClass();
        courseClass2.setName("두번째 수업을 만들어주세요");
        courseClass2.setMediaPath("https://res.cloudinary.com/dg8tebwjm/video/upload/v1622075948/78030bfba74f5bfd625ecb75755f3b8073f351ab4a6bce15438f7d157e93e891.mp4");
        CourseClass savedCourseClass2 = courseClassRepository.save(courseClass2);
        /* 테스트용 반드시 제거할 것  끝 */

        CourseSection courseSection = new CourseSection();
        courseSection.setName("첫번째 섹션의 제목을 입력해주세요.");
        courseSection.addCourseClass(savedCourseClass);
        /* 테스트용 반드시 제거할 것 시작 */
        courseSection.addCourseClass(savedCourseClass2);
        /* 테스트용 반드시 제거할 것  끝 */
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

    /***
     * 강사가 강의를 삭제함
     * @param id 강의 pk
     */
    @Transactional
    public void deleteCourse(Long id) {
        Course findCourse = courseRepository.findById(id).orElseThrow(() -> new NoSearchCourseException());
        findCourse.deleteCourse();
        courseRepository.delete(findCourse);
    }

    /***
     * 모든 강의를 조회
     * @return
     */
    @Transactional
    public List<CourseDTO> getCourses(Pageable pageable) {
        List<CourseDTO> courseDTOList = courseRepository.findAll(pageable).stream().map(c ->
                c.of()).collect(Collectors.toList());
        return courseDTOList;
    }

    /***
     * 강의 상태를 변경
     * @param id 강의 pk
     * @param courseState 변경할 상태 객체
     */
    @Transactional
    public void changeCourseState(Long id, CourseState courseState){
        Course savedCourse = courseRepository.findById(id).orElseThrow(() -> new NoSearchCourseException());
        savedCourse.setCourseState(courseState);
    }

    /***
     * index 페이지에서 RELEASE 상태의 강의를 12개 조회
     * @return
     */
    @Transactional
    public List<CourseDTO> getCourseList(){
        CourseState courseState = CourseState.RELEASE;
        List<Course> list = courseRepository.findTop12ByCourseState(courseState);
        List<CourseDTO> courseDTOList = list.stream().map(c -> c.of()).collect(Collectors.toList());

        return courseDTOList;
    }

    /***
     * 강의 검색 기능
     * @param value 검색어
     */
    @Transactional
    public List<CourseDTO> getCourseList(String value) {
        List<Course> list = courseRepository.findByNameContainingIgnoreCase(value);
        List<CourseDTO> courseDTOList = list.stream().map(c -> c.of()).collect(Collectors.toList());
        return courseDTOList;
    }

    /***
     * 강의 pk로 url을 찾음
     * @param id pk
     * @return
     */
    @Transactional
    public String findById(Long id){
        Course savedCourse = courseRepository.findById(id).orElseThrow(() -> new NoSearchCourseException());
        return savedCourse.getUrl();
    }

    @Transactional
    public void deleteCart(Member member, Long id){
        Member savedMember = memberRepository.findById(member.getId()).orElseThrow(() ->
                new UsernameNotFoundException("해당하는 유저가 존재하지 않습니다."));

        Course savedCourse = courseRepository.findById(id).orElseThrow(() ->
                new NoSearchCourseException());

        Cart savedCart = cartRepository.findById(savedMember.getCart().getId()).orElse(null);

        savedCart.deleteCart(savedMember, savedCourse);
        /* AuthenticationToken 수정 */

        Authentication beforeAuthentication = SecurityContextHolder.getContext().getAuthentication();
        Member authenticationObject = ((Member)beforeAuthentication.getPrincipal());
        authenticationObject.setCart(savedCart);
        Authentication afterAuthentication = new UsernamePasswordAuthenticationToken(authenticationObject, null, beforeAuthentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(afterAuthentication);
    }

    /***
     * 사용자가 장바구니에 담은 강의들을 구매했음
     * @param member 사용자
     * @param oldPayment 구매정보
     * @param list 구매한 강의 pk List
     */
    @Transactional
    public void purchaseCourse(Member member, Payment oldPayment, List<Long> list) {
        Payment payment = new Payment();
        Payment savedPayment = paymentRepository.save(payment);

        savedPayment.setMethod(oldPayment.getMethod());
        savedPayment.setCardName(oldPayment.getCardName());
        savedPayment.setImp_uid(oldPayment.getImp_uid());
        savedPayment.setMerchantId(oldPayment.getMerchantId());
        savedPayment.setCardNumber(oldPayment.getCardNumber());
        savedPayment.setCourseName(oldPayment.getCourseName());
        savedPayment.setCoursePrice(oldPayment.getCoursePrice());
        savedPayment.setPurchaseStatus(oldPayment.getPurchaseStatus());
        savedPayment.setPurchaseResult(oldPayment.isPurchaseResult());

        Member savedMember = memberRepository.findById(member.getId()).orElseThrow(() ->
                new UsernameNotFoundException("해당하는 유저가 존재하지 않습니다."));
        savedMember.addPayment(savedPayment);
        savedPayment.setMember(savedMember);

        for(int i=0;i<list.size();i++){
            Course savedCourse = courseRepository.findById(list.get(i)).orElseThrow(() ->
                    new NoSearchCourseException());

            savedPayment.addCourse(savedCourse);

            /* 수강신청 */
            SignUpCourse signUpCourse = new SignUpCourse();
            SignUpCourse savedSignUpCourse = signUpCourseRepository.save(signUpCourse);

            savedSignUpCourse.setMember(savedMember);
            savedSignUpCourse.setCourse(savedCourse);

            savedMember.addSignUpCourse(savedSignUpCourse);
            savedCourse.addSignUpCourse(savedSignUpCourse);
        }

        Long cart_id = savedMember.getCart().getId();

        /* 장바구니 초기화 */
        Cart savedCart = cartRepository.findById(cart_id).orElseThrow(() ->
                new RuntimeException());
        log.info(savedCart.getCourses().stream().map(c-> c.getName()).collect(Collectors.joining(", ")));

        savedCart.deleteCarts();
        cartRepository.deleteById(savedCart.getId());

        /* AuthenticationToken 수정 */

        Authentication beforeAuthentication = SecurityContextHolder.getContext().getAuthentication();
        Member authenticationObject = ((Member)beforeAuthentication.getPrincipal());
        authenticationObject.setCart(null);
        Authentication afterAuthentication = new UsernamePasswordAuthenticationToken(authenticationObject, null, beforeAuthentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(afterAuthentication);
    }
}
