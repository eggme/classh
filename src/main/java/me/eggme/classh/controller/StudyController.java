package me.eggme.classh.controller;

import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.CourseClassDTO;
import me.eggme.classh.domain.dto.CourseDTO;
import me.eggme.classh.domain.entity.Course;
import me.eggme.classh.domain.entity.CourseClass;
import me.eggme.classh.service.CourseService;
import me.eggme.classh.service.MemberService;
import me.eggme.classh.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/study")
@Slf4j
public class StudyController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudyService studyService;

    @GetMapping(value="/{id}/preview/{class_id}")
    public String coursePreview(@PathVariable Long id,
                                @PathVariable Long class_id,
                                Model model){
        Course course = courseService.getCourse(id);
        CourseClass courseClass = courseService.getCourseClass(class_id);
        CourseDTO courseDTO = course.of();
        CourseClassDTO courseClassDTO = courseClass.of();
        model.addAttribute("course", courseDTO);
        model.addAttribute("courseClass", courseClassDTO);
        return "study/studyRoom/"+courseClassDTO.getName();
    }
}
