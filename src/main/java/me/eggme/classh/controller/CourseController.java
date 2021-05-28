package me.eggme.classh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.*;
import me.eggme.classh.domain.entity.*;
import me.eggme.classh.service.CourseReviewService;
import me.eggme.classh.service.CourseService;
import me.eggme.classh.service.MemberService;
import me.eggme.classh.utils.CourseValidation;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/course")
@Slf4j
public class CourseController {

    @Autowired
    public MemberService memberService;

    @Autowired
    public CourseService courseService;
    @Autowired
    public CourseReviewService courseReviewService;

    private static EnumSet categories = EnumSet.allOf(CourseCategory.class);
    private static EnumSet levels = EnumSet.allOf(CourseLevel.class);

    // 지식공유자 강의 생성 페이지 뷰
    @GetMapping(value = "/add")
    public String createCourseView(Model model, @AuthenticationPrincipal Member member){
        String username = memberService.loadUserNickname(member.getUsername());
        model.addAttribute("username", username);
        return "create/createCourse";
    }

    // 지식공유자 강의 기본 생성 (강의 이름만 존재)
    @PostMapping(value = "/create")
    public String createLecture(@RequestParam(value = "course_name") String courseName, @AuthenticationPrincipal Member member, Model model){
        Course course = courseService.createCourseDefault(courseName, member.getUsername());
        CourseDTO courseDTO = course.of();
        model.addAttribute("course", courseDTO);
        model.addAttribute("categories", categories);
        model.addAttribute("level", levels);
        model.addAttribute("category", "course_info");
        return "course/addCourse";
    }

    // 내 강의 보기 강사만 입장 가능 (강의소개)
    @GetMapping(value = "/{url}")
    public String updateCourse(@PathVariable String url, Model model,
                               @AuthenticationPrincipal Member member){

        Course course = courseService.getCourse(url);
        Member loadMember = null;
        try{
            loadMember = memberService.loadUser(member.getUsername());
        }catch (NullPointerException n){}
        if(loadMember != null) {
            MemberHistoryDTO memberHistoryDTO = memberService.getMemberHistory(loadMember.getId());
            model.addAttribute("courseHistory", memberHistoryDTO); // 수강관련
            model.addAttribute("member", member.of());
        }

        CourseDTO courseDTO = course.of();
        model.addAttribute("course", courseDTO);

        /* 모달 관련 */
        String modal = model.asMap().get("modal") == null ? null : model.asMap().get("modal").toString();
        if(modal != null) model.addAttribute("modal", modal);

        return "information/courseInfo/info";
    }

    // 내 강의 보기 (대시보드)  dashboard
    @GetMapping(value = "/{url}/dashboard")
    @PreAuthorize("isAuthenticated()")
    public String courseDashBoard(@PathVariable String url, @AuthenticationPrincipal Member member, Model model){
        MemberHistoryDTO memberHistoryDTO = memberService.getMemberHistory(member.getId());
        Course course = courseService.getCourse(url);
        CourseDTO courseDTO = course.of();
        model.addAttribute("courseHistory", memberHistoryDTO);
        model.addAttribute("course", courseDTO);
        return "information/courseDashboard/dashboard";
    }

    // 내 강의 보기 (수강생 관리)  management
    @GetMapping(value = "/{url}/management")
    @PreAuthorize("isAuthenticated()")
    public String courseManagement(@PathVariable String url, @AuthenticationPrincipal Member member, Model model){
        MemberHistoryDTO memberHistoryDTO = memberService.getMemberHistory(member.getId());
        model.addAttribute("courseHistory", memberHistoryDTO); // 수강관련
        Course course = courseService.getCourse(url);
        CourseDTO courseDTO = course.of();
        model.addAttribute("course", courseDTO);
        return "information/courseManagement/management";
    }

    // 내 강의 만들기에서 다이렉트로 상세소개 클릭 시
    @GetMapping(value="/{id}/description")
    public String courseDescriptionView(@PathVariable Long id, Model model){
        Course course = courseService.getCourse(id);
        CourseDTO courseDTO = course.of();
        model.addAttribute("course", courseDTO);
        model.addAttribute("category", "description");
        return "course/courseDescription";
    }

    // 내 강의만들기에서 강의 정보 저장 후 다음 이동을 했을 때의 핸들러
    @PostMapping(value = "/{id}/save/information")
    public String savedCourseInformation(@ModelAttribute Course course,
                              @RequestParam(value = "courseCategory", required = false) CourseCategory courseCategory,
                              @RequestParam(value = "courseLevel",  required = false) CourseLevel courseLevel,
                              @RequestParam(value = "recommendations",  required = false) List<Recommendation> recommendations,
                              @RequestParam(value = "tags",  required = false) List<SkillTag> skillTags,
                              Model model){
        Course editedCourse = courseService.editCourse(course, courseCategory, courseLevel, recommendations, skillTags);
        CourseDTO courseDTO = editedCourse.of();
        model.addAttribute("course", courseDTO);
        model.addAttribute("category", "description");
        return "course/courseDescription";
    }

    // 내 강의 만들기에서 커리큘럼을 눌렀을 때 뷰
    @GetMapping(value = "{id}/curriculum")
    public String courseCurriculumView(@PathVariable Long id, Model model){
        Course course = courseService.getCourse(id);
        CourseDTO courseDTO = course.of();
        model.addAttribute("course", courseDTO);
        model.addAttribute("category", "curriculum");
        return "course/courseCurriculumn";
    }

    // 내 강의 만들기에서 상세소개에서 저장 후 다음이동을 했을 때의 핸들러
    @PostMapping(value = "/{id}/save/description")
    public String saveCourseDescription(@ModelAttribute Course course, Model model){
        Course updatedCourse = courseService.editCourse(course);
        CourseDTO courseDTO = updatedCourse.of();
        model.addAttribute("course", courseDTO);
        model.addAttribute("category", "curriculum");
        return "course/courseCurriculumn";
    }

    // ajax 요청 : 강사가 섹션을 추가함
    @PostMapping(value = "/{id}/save/section")
    @ResponseBody
    public CourseSectionDTO saveCourseSection(@PathVariable Long id, @ModelAttribute CourseSection courseSection) throws Exception{
        Course course = courseService.getCourse(id);
        CourseSection createdSection = courseService.createSection(course, courseSection);
        CourseSectionDTO courseSectionDTO = createdSection.of();
        return courseSectionDTO;
    }

    // ajax 요청 : 강사가 섹션을 수정함
    @PostMapping(value = "/edit/section")
    @ResponseBody
    public CourseSectionDTO editCourseSection(@ModelAttribute CourseSection courseSection){
        CourseSection createdSection = courseService.editSection(courseSection);
        CourseSectionDTO courseSectionDTO = createdSection.of();
        return courseSectionDTO;
    }

    /***
     *  ajax 요청 : 강사가 섹션을 삭제함
     */
    @PostMapping(value = "/{id}/delete/section")
    @ResponseBody
    public String deleteCourseSection(@PathVariable Long id){
        courseService.deleteCourseSection(id);
        return "success";
    }

    /***
     *  ajax 요청 : 강사가 수업을 삭제함
     */
    @PostMapping(value = "/{id}/delete/class")
    @ResponseBody
    public String deleteCourseClass(@PathVariable Long id){
        courseService.deleteCourseClass(id);
        return "success";
    }

    // ajax 요청 : 강사가 수업을 추가함
    @PostMapping(value = "/{id}/save/class")
    @ResponseBody
    public CourseClassDTO saveCourseClass(@ModelAttribute CourseClass courseClass, @RequestParam Long course_section_id){
        CourseClass createdClass = courseService.createClass(courseClass, course_section_id);
        CourseClassDTO courseClassDTO = createdClass.of();
        return courseClassDTO;
    }

    // ajax 요청 : 강사가 수업에 영상을 등록 /course/"+id+"/upload/video/" + class_id + "/" + duration
    @PostMapping(value = "/{id}/upload/video/{classId}/{duration}")
    @ResponseBody
    public CourseClassDTO uploadClassVideo(@RequestParam(value = "file") MultipartFile multipartFile,
                                   @PathVariable(value = "classId") Long class_id,
                                   @PathVariable(value = "duration") double duration,
                                           HttpServletRequest request) throws IOException{
        String realPath = request.getRealPath("/imgs/upload");
        File file = new File(realPath+"\\"+multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        CourseClassDTO courseClassDTO = courseService.saveCourseVideo(class_id, file, duration);
        return courseClassDTO;
    }
    // ajax 요청 : 강사가 수업에 필요한 강의자료를 올림
    @PostMapping(value = "/upload/file/{classId}")
    @ResponseBody
    public CourseClassDTO uploadClassStudyFile(@RequestParam(value = "file") MultipartFile multipartFile,
                                               @PathVariable(value = "classId") Long class_id,
                                               HttpServletRequest request) throws IOException{
        String realPath = request.getRealPath("/imgs/upload");
        File file = new File(realPath+"\\"+multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        CourseClassDTO courseClassDTO = courseService.saveCourseStudyFile(class_id, file);
        return courseClassDTO;
    }

    // 수강생이 강의 정보에서 리뷰를 입력함 ???
    @PostMapping(value = "/{id}/add/review")
    public String addCourseReview(@PathVariable Long id,
                                  @RequestParam int rate,
                                  @RequestParam String reviewContent,
                                  Model model, @AuthenticationPrincipal Member member){
        Course course = courseService.saveCourseReview(id, member.getUsername(), rate, reviewContent);
        Member loadMember = memberService.loadUser(member.getUsername());
        String url = course.getUrl();
        return "redirect:/course/"+url;
    }

    // 강사가 강의를 편집하고 저장할 때 콜
    @PostMapping(value = "/edit/class/info")
    public String editCourseClassInfo(@ModelAttribute CourseClass courseClass, Model model){
        Course findCourse = courseService.editCourseClass(courseClass);
        CourseDTO courseDTO = findCourse.of();
        model.addAttribute("course", courseDTO);
        model.addAttribute("category", "curriculum");
        return "course/courseCurriculumn";
    }


    @PostMapping(value = "/edit/class")
    @ResponseBody
    public CourseClassDTO getCourseClassData(@RequestParam(value = "class_id") Long class_id){
        CourseClass findClass = courseService.getCourseClass(class_id);
        CourseClassDTO courseClassDTO = findClass.of();
        return courseClassDTO;
    }

    // 강사가 강의수정 버튼 클릭 시 뷰
    @GetMapping(value = "/{id}/edit/course_info")
    public String editCourse(@PathVariable Long id, Model model){
        Course course = courseService.getCourse(id);
        CourseDTO courseDTO = course.of();
        model.addAttribute("course", courseDTO);
        model.addAttribute("categories", categories);
        model.addAttribute("level", levels);
        model.addAttribute("category", "course_info");
        return "course/addCourse";
    }

    // 지식공유자 내 강의 보기
    @GetMapping(value = "/list")
    public String courseList(@AuthenticationPrincipal Member member, Model model){
        List<Course> courses = courseService.getCourses(member.getUsername());
        List<CourseDTO> list = null;
        if(courses != null) {
             list = courses.stream().map(c -> c.of()).collect(Collectors.toList());
        }
        model.addAttribute("list", list);
        return "instructor/courseList";
    }

    // 지식공유자 대시보드 클릭
    @GetMapping(value = "/dashboard")
    public String dashboardView(){
        return "instructor/instructorDashboard";
    }

    /**
     * 강의 상세 정보 입력에서 에디터로 사진 업로드 시 호출
     * @param multipartFile 사진 file 객체
     * @param request 페이지 요청 request 객체
     * @return
     * @throws IOException
     */

    @PostMapping(value = "/upload/img")
    @ResponseBody
    public String uploadDescriptionImage(@RequestParam(value = "file")MultipartFile multipartFile,
                                         HttpServletRequest request) throws IOException {
        String realPath = request.getRealPath("/imgs/upload");
        File file = new File(realPath+"\\"+multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("data_url", courseService.uploadCourseDescriptionImage(file));

        return jsonObject.toString();
    }

    /**
     * 해당 강의 썸네일 요청
     * @param id 해당 강의의 pk
     * @param model 데이터를 담을 model 객체
     * @return
     */
    @GetMapping(value = "/{id}/thumbnail")
    public String getCourseThumbnailView(@PathVariable Long id, Model model){
        Course course = courseService.getCourse(id);
        model.addAttribute("course", course.of());
        model.addAttribute("category", "thumbnail_");
        return "course/courseImage";
    }

    /** Ajax로 강의 썸네일 업로드
     *
     * @param multipartFile 이미지 file 객체
     * @param id 해당하는 강의의 pk
     * @param request 페이지 요청 request 객체
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/{id}/upload/thumbnail")
    @ResponseBody
    public CourseDTO uploadCourseThumbnail(@RequestParam(value = "file") MultipartFile multipartFile,
                                        @PathVariable Long id,
                                        HttpServletRequest request) throws IOException{

        String realPath = request.getRealPath("/imgs/upload");
        File file = new File(realPath+"\\"+multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);

        Course course = courseService.saveCourseThumbnail(id, file);
        CourseDTO courseDTO = course.of();
        return courseDTO;
    }

    /***
     * 강사가 최종 제출 버튼을 눌렀을 때
     */
    @PostMapping(value = "/confirm")
    @ResponseBody
    public String courseConfirm(Long id){
        courseService.submitted(id);
        return "success";
    }

    /***
     * 수강생이 본인 수강평을 편집할 때 본인 수강평 정보를 조회하는 메서드
     */
    @PostMapping(value = "/select/review")
    @ResponseBody
    public CourseReviewDTO selectReview(@RequestParam(value = "review_id") Long review_id){
        CourseReview savedReview = courseReviewService.selectReview(review_id);
        CourseReviewDTO courseReviewDTO = savedReview.of();
        return courseReviewDTO;
    }

    @PostMapping(value = "/delete/review")
    @ResponseBody
    public String deleteReview(@RequestParam(value = "review_id") Long review_id){
        courseReviewService.deleteReview(review_id);
        return "success";
    }

    /***
     * 수강생이 본인 수강평을 수정함
     */
    @PostMapping(value = "/edit/review")
    public String editReview(@ModelAttribute CourseReview courseReview,
                             @RequestParam(value = "course_id") Long course_id,
                             Model model){
        courseReviewService.editReview(courseReview);
        Course savedCourse = courseService.getCourse(course_id);
        String url = savedCourse.getUrl();
        return "redirect:/course/"+url;
    }

    /***
     *  강사가 해당 강의를 삭제함
     */
    @PostMapping(value = "/delete")
    @ResponseBody
    public String deleteCourse(@RequestParam(value = "id") Long id){
        courseService.deleteCourse(id);
        return "success";
    }

    /***
     * ajax로 메인페이지에서 사용자가 강의를 검색함
     * @param value
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/search")
    @ResponseBody
    public String searchCourse(@RequestParam(value = "search_value") String value) throws JsonProcessingException {
        List<CourseDTO> list = courseService.getCourseList(value);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(list);
    }

    /***
     * 메인페이지에서 강의를 검색함
     * @param value
     * @param model
     * @return
     */
    @GetMapping(value = "/search")
    public String searchCourse(@RequestParam(value = "s") String value, Model model){
        List<CourseDTO> list = courseService.getCourseList(value);
        model.addAttribute("list", list);
        return "search/searchCourse/"+value;
    }


    /***
     * 사용자가 강의를 장바구니에 넣음
     * @param member 사용자
     * @param id 강의 pk
     * @return
     */
    @PostMapping(value = "/add/cart")
    @PreAuthorize("isAuthenticated()")
    @Transactional
    public String addCart(@AuthenticationPrincipal Member member,
                          @RequestParam(value = "course_id") Long id,
                          RedirectAttributes redirectAttributes){
        memberService.addCourseCart(member, id);
        String url = courseService.findById(id);
        redirectAttributes.addFlashAttribute("modal", "success");
        return "redirect:/course/"+url;
    }

    /***
     * ajax 요청, header에 있는 장바구니에 마우스 hover시 유저의 장바구니 목록을 조회
     * @param member 유저
     * @return 장바구니에 담긴 강의들
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/select/cart")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public String selectCart(@AuthenticationPrincipal Member member) throws JsonProcessingException {
        Set<Course> courses = memberService.selectCart(member);
        ObjectMapper mapper = new ObjectMapper();
        if(courses != null){
            Set<CourseMappingDTO> dtoSet = courses.stream().map(c-> c.mapping()).collect(Collectors.toSet());

            return mapper.writeValueAsString(dtoSet);
        }
        HashMap map = new HashMap();
        map.put("result", "error");
        return mapper.writeValueAsString(map);
    }

    /***
     * 장바구니 페이지 요청, 사용자의 장바구니를 조회
     * @param member 사용자
     * @param model
     * @return
     */
    @GetMapping(value = "/carts")
    @PreAuthorize("isAuthenticated()")
    public String carts(@AuthenticationPrincipal Member member, Model model){
        Set<Course> courses = memberService.selectCart(member);
        if(courses != null){
            Set<CourseMappingDTO> dtoSet = courses.stream().map(c-> c.mapping()).collect(Collectors.toSet());
            model.addAttribute("list", dtoSet);
        }
        return "inst/courseCart";
    }

    @PostMapping(value = "/payment")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public String payments(@AuthenticationPrincipal Member member,
                           @ModelAttribute Payment payment,
                           @RequestParam(value = "course_list") String course_ids) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Long>[] courseMap = mapper.readValue(course_ids, new TypeReference<Map<String, Long>[]>() {});
        List<Long> courseIdList = Arrays.stream(courseMap).map(cm -> cm.get("course_id")).collect(Collectors.toList());

        courseService.purchaseCourse(member, payment, courseIdList);

        Map<String, String> map = new HashMap<>();
        map.put("result", "success");

        return mapper.writeValueAsString(map);
    }
}
