package me.eggme.classh.controller;

import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.CourseDTO;
import me.eggme.classh.domain.entity.Course;
import me.eggme.classh.domain.entity.Member;
import me.eggme.classh.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/md")
@Secured("ROLE_MD")
@Slf4j
public class MdController {

    @Autowired
    private CourseService courseService;

    @GetMapping(value = "/dashboard")
    public String mdDashboard(@AuthenticationPrincipal Member member, Model model){
        model.addAttribute("member", member);
        return "md/dashboard";
    }

    @GetMapping(value = "/course/list")
    public String mdCourseList(Pageable pageable, Model model){
        List<CourseDTO> list = courseService.getCourses(pageable);
        log.info("강의 개수 : " +list.size());
        list.forEach(c-> log.info(c.toString()));
        model.addAttribute("list", list);
        return "md/menu/courseList";
    }

    @GetMapping(value = "/info/{id}")
    public String courseInfo(@PathVariable Long id, Model model){
        Course course = courseService.getCourse(id);
        CourseDTO courseDTO = course.of();
        model.addAttribute("course", courseDTO);
        return "md/menu/courseInfo";
    }
}
