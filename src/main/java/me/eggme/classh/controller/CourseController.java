package me.eggme.classh.controller;

import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j2;
import me.eggme.classh.dto.*;
import me.eggme.classh.entity.*;
import me.eggme.classh.service.CourseService;
import me.eggme.classh.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.EnumSet;
import java.util.List;

@Controller
@RequestMapping("/course")
@Log4j2
public class CourseController {

    @Autowired
    public MemberService memberService;

    @Autowired
    public CourseService courseService;

    private static EnumSet categories = EnumSet.allOf(CourseCategory.class);
    private static EnumSet levels = EnumSet.allOf(CourseLevel.class);

    // 지식공유자 강의 생성 페이지 뷰
    @GetMapping(value = "/add")
    public String createCourseView(Model model, HttpSession session){
        String email = session.getAttribute("username").toString();
        String username = memberService.loadUserName(email);
        model.addAttribute("username", username);
        return "create/createCourse";
    }

    // 지식공유자 강의 기본 생성 (강의 이름만 존재)
    @PostMapping(value = "/create")
    public String createLecture(@RequestParam(value = "course_name") String courseName, HttpSession session, Model model){
        String email = session.getAttribute("username").toString();
        Course course = courseService.createCourseDefault(courseName, email);
        CourseDTO courseDTO = course.of();
        model.addAttribute("course", courseDTO);
        model.addAttribute("category", categories);
        model.addAttribute("level", levels);
        return "course/addCourse";
    }

    // 내 강의 보기 강사만 입장 가능
    @GetMapping(value = "/{url}")
    public String updateCourse(@PathVariable String url, Model model){
        Course course = courseService.getCourse(url);
        CourseDTO courseDTO = course.of();
        model.addAttribute("course", courseDTO);
        return "information/courseInfo/dashboard";
    }

    // 강의 만들기에서 다이렉트로 상세소개 클릭 시
    @GetMapping(value="/{id}/description")
    public String courseDecriptionView(@PathVariable Long id, Model model){
        Course course = courseService.getCourse(id);
        CourseDTO courseDTO = course.of();
        model.addAttribute("course", courseDTO);
        return "course/courseDescription";
    }

    // 내 강의만들기에서 강의 정보 저장 후 다음 이동을 했을 때의 핸들러
    @PostMapping(value = "/{id}/save/information")
    public String savedCourseInformation(@ModelAttribute Course course,
                              @RequestParam(value = "courseCategory") CourseCategory courseCategory,
                              @RequestParam(value = "courseLevel") CourseLevel courseLevel,
                              @RequestParam(value = "recommendations") List<Recommendation> recommendations,
                              @RequestParam(value = "tags") List<Tag> tags,
                              Model model){
        Course editedCourse = courseService.editCourse(course, courseCategory, courseLevel, recommendations, tags);
        CourseDTO courseDTO = editedCourse.of();
        model.addAttribute("course", courseDTO);
        return "course/courseDescription";
    }

    // 내 강의 만들기에서 커리큘럼을 눌렀을 때 뷰
    @GetMapping(value = "{id}/curriculum")
    public String courseCurriculumView(@PathVariable Long id, Model model){
        Course course = courseService.getCourse(id);
        model.addAttribute("course", course);
        return "course/courseCurriculumn";
    }

    // 내 강의 만들기에서 상세소개에서 저장 후 다음이동을 했을 때의 핸들러
    @PostMapping(value = "/{id}/save/description")
    public String saveCourseDescription(@ModelAttribute Course course, Model model){
        Course updatedCourse = courseService.editCourse(course);
        model.addAttribute("course", updatedCourse);
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

    // 강사가 강의를 편집하고 저장할 때 콜
    @PostMapping(value = "/edit/class/info")
    public String editCourseClassInfo(@ModelAttribute CourseClass courseClass, Model model){
        log.info(courseClass);
        Course findCourse = courseService.editCourseClass(courseClass);
        model.addAttribute("course", findCourse);
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
        model.addAttribute("category", categories);
        model.addAttribute("level", levels);
        return "course/addCourse";
    }

    // 지식공유자 내 강의 보기
    @GetMapping(value = "/list")
    public String courseList(HttpSession session, Model model){
        String email = session.getAttribute("username").toString();
        model.addAttribute("list", courseService.getCourses(email));
        return "instructor/courseList";
    }

    // 지식공유자 대시보드 클릭
    @GetMapping(value = "/dashboard")
    public String dashboardView(){
        return "instructor/instructorDashboard";
    }

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
}
