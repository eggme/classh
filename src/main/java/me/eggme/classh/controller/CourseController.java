package me.eggme.classh.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j2;
import me.eggme.classh.dto.CourseCategory;
import me.eggme.classh.dto.CourseClassDTO;
import me.eggme.classh.dto.CourseLevel;
import me.eggme.classh.dto.CourseSectionDTO;
import me.eggme.classh.entity.*;
import me.eggme.classh.service.CourseService;
import me.eggme.classh.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import retrofit2.http.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        model.addAttribute("course", course);
        model.addAttribute("category", categories);
        model.addAttribute("level", levels);
        return "course/addCourse";
    }

    // 내 강의 보기 강사만 입장 가능
    @GetMapping(value = "/{url}")
    public String updateCourse(@PathVariable String url, Model model){
        Course course = courseService.getCourse(url);
        model.addAttribute("course", course);
        return "information/courseInfo/dashboard";
    }

    // 강의 만들기에서 다이렉트로 상세소개 클릭 시
    @GetMapping(value="/{id}/description")
    public String courseDecriptionView(@PathVariable Long id, Model model){
        Course course = courseService.getCourse(id);
        model.addAttribute("course", course);
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
        model.addAttribute("course", editedCourse);
        return "course/courseDescription";
    }

    // 내 강의 만들기에서 커리큘럼을 눌렀을 때 뷰
    @GetMapping(value = "{id}/curriculum")
    public String courseCurriculumView(@PathVariable Long id, Model model){
        Course course = courseService.getCourse(id);
        log.info(course.getCourseSections());
        course.getCourseSections().stream().forEach(s -> System.out.println(s));
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
        CourseSectionDTO result = createdSection.of();
        return result;
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
    public CourseClassDTO uploadClassVideo(@PathVariable(value = "id") Long id,
                                           @RequestParam(value = "file") MultipartFile multipartFile,
                                           @PathVariable(value = "classId") Long class_id,
                                           @PathVariable(value = "duration") double duration){
        log.info(id+ " : " + class_id + " : " + duration + " : " + multipartFile.getName());
        /* 여기부터 진행 해야합니다
            id = 강의 pk
            file = 영상
            class_id = course_class_id
            duration = 영상 길이
        */
        return null;
    }

    // 강사가 강의수정 버튼 클릭 시 뷰
    @GetMapping(value = "/{id}/edit/course_info")
    public String editCourse(@PathVariable Long id, Model model){
        Course course = courseService.getCourse(id);
        model.addAttribute("course", course);
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
