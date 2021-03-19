package me.eggme.classh.controller;

import lombok.extern.log4j.Log4j2;
import me.eggme.classh.dto.CourseCategory;
import me.eggme.classh.dto.CourseLevel;
import me.eggme.classh.entity.Course;
import me.eggme.classh.entity.Recommendation;
import me.eggme.classh.entity.Tag;
import me.eggme.classh.service.CourseService;
import me.eggme.classh.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
        log.info(email);
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

    // 내 강의만들기에서 저장 후 다음 이동을 했을 때의 핸들러
    @PostMapping(value = "/{id}/edit/saved")
    public String savedCourse(@ModelAttribute Course course,
                              @RequestParam(value = "courseCategory") CourseCategory courseCategory,
                              @RequestParam(value = "courseLevel") CourseLevel courseLevel,
                              @RequestParam(value = "recommendations") List<Recommendation> recommendations,
                              @RequestParam(value = "tags") List<Tag> tags,
                              Model model, HttpSession session){
        log.info(course);
        log.info(courseCategory);
        log.info(courseLevel);
        log.info(recommendations);
        log.info(tags);
        Course editedCourse = courseService.editCourse(course, courseCategory, courseLevel, recommendations, tags);
        model.addAttribute("course", editedCourse);
        return "course/courseDescription";
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
    public String dashboardView(HttpSession session, Model model){
        return "instructor/instructorDashboard";
    }

    @PostMapping(value = "/upload/img")
    public String uploadDescriptionImage(HttpSession session){
        String email = session.getAttribute("username").toString();

        return "";
    }
}
