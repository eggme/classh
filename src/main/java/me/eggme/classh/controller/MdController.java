package me.eggme.classh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.CourseDTO;
import me.eggme.classh.domain.dto.CourseState;
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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/md")
@Secured("ROLE_MD")
@Slf4j
public class MdController {

    @Autowired
    private CourseService courseService;

    @GetMapping(value = "/dashboard")
    public String mdDashboard(@AuthenticationPrincipal Member member, Model model){

        List<CourseDTO> submitList = courseService.getSubmitCourses();
        model.addAttribute("submitList", submitList);


        model.addAttribute("member", member);
        return "md/dashboard";
    }

    @GetMapping(value = "/course/list")
    public String mdCourseList(Pageable pageable, Model model){
        List<CourseDTO> list = courseService.getCourses(pageable);
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

    @PostMapping(value = "/course/change")
    @ResponseBody
    public String changeCourseState(@RequestParam Long id,
                                    @RequestParam CourseState courseState) throws JsonProcessingException {
        log.info(courseState.getValue());
        courseService.changeCourseState(id, courseState);

        Map<String, String> map = new HashMap<>();
        map.put("result", "success");

        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(map);
    }
}
