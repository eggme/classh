package me.eggme.classh.controller;

import lombok.extern.log4j.Log4j2;
import me.eggme.classh.entity.Course;
import me.eggme.classh.service.CourseService;
import me.eggme.classh.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/course")
@Log4j2
public class CourseController {

    @Autowired
    public MemberService memberService;

    @Autowired
    public CourseService courseService;

    @GetMapping(value = "/add")
    public String createCourseView(Model model, HttpSession session){
        String email = session.getAttribute("username").toString();
        log.info(email);
        String username = memberService.loadUserName(email);
        model.addAttribute("username", username);
        return "create/createCourse";
    }

    @PostMapping(value = "/create")
    public String createLecture(@RequestParam(value = "course_name") String courseName, HttpSession session, Model model){
        String email = session.getAttribute("username").toString();
        // 2021-03-12 작업
        Course course = courseService.createCourseDefault(courseName, email);
        model.addAttribute("course", course);
        return "create/addCourse";
    }

    @GetMapping(value = "/list")
    public String courseList(HttpSession session){
        String email = session.getAttribute("username").toString();

        return "board/courseList";
    }

    @PostMapping(value = "/upload/img")
    public String uploadDescriptionImage(HttpSession session){
        String email = session.getAttribute("username").toString();

        return "";
    }
}
