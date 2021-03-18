package me.eggme.classh.controller;

import lombok.extern.log4j.Log4j2;
import me.eggme.classh.entity.Course;
import me.eggme.classh.service.CourseService;
import me.eggme.classh.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/course")
@Log4j2
public class CourseController {

    @Autowired
    public MemberService memberService;

    @Autowired
    public CourseService courseService;

    // 지식공유자 강의 생성 페이지 뷰
    @GetMapping(value = "/add")
    public String createCourseView(Model model, HttpSession session){
        String email = session.getAttribute("username").toString();
        log.info(email);
        String username = memberService.loadUserName(email);
        model.addAttribute("username", username);
        return "create/createCourse";
    }

    //

    // 지식공유자 강의 기본 생성 (강의 이름만 존재)
    @PostMapping(value = "/create")
    public String createLecture(@RequestParam(value = "course_name") String courseName, HttpSession session, Model model){
        String email = session.getAttribute("username").toString();
        log.info(courseName + " : " + email);
        // 2021-03-12 작업
        Course course = courseService.createCourseDefault(courseName, email);
        model.addAttribute("course", course);
        return "course/addCourse";
    }

    // 내 강의 보기 강사만 입장 가능
    @GetMapping(value = "/{url}")
    public String updateCourse(@PathVariable String url, Model model){
        log.info(url);
        Course course = courseService.getCourse(url);
        model.addAttribute("course", course);
        return "information/courseInfo/dashboard";
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
