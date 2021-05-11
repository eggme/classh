package me.eggme.classh.controller;

import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.CourseClassDTO;
import me.eggme.classh.domain.dto.CourseDTO;
import me.eggme.classh.domain.dto.CourseQuestionDTO;
import me.eggme.classh.domain.dto.MemberDTO;
import me.eggme.classh.domain.entity.Course;
import me.eggme.classh.domain.entity.CourseQuestion;
import me.eggme.classh.domain.entity.Member;
import me.eggme.classh.service.CourseQuestionService;
import me.eggme.classh.service.CourseService;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/question")
@Slf4j
public class CourseQuestionController {

    @Autowired
    private CourseQuestionService courseQuestionService;
    @Autowired
    private CourseService courseService;

    @GetMapping(value = "/{id}")
    public String getCourseQuestion(@PathVariable Long id, Model model){
        CourseQuestion savedCourseQuestion = courseQuestionService.getCourseQuestion(id);
        CourseQuestionDTO courseQuestionDTO = savedCourseQuestion.of();
        CourseDTO courseDTO = courseQuestionDTO.getCourse().of();
        MemberDTO memberDTO = courseQuestionDTO.getMember().of();
        model.addAttribute("courseQuestion", courseQuestionDTO);
        model.addAttribute("course", courseDTO);
        model.addAttribute("member", memberDTO);

        if(courseQuestionDTO.getCourseClass() != null){
            CourseClassDTO courseClassDTO = courseQuestionDTO.getCourseClass().of();
            model.addAttribute("courseClass", courseClassDTO);
        }

        return "questions/courseQnA";
    }

    @GetMapping(value = "/select/{id}")
    public String selectCourseQuestion(Model model, @PathVariable(value = "id") Long course_id){

        List<CourseQuestion> savedCourseQuestions = courseQuestionService.selectCourseQuestions(course_id);
        List<CourseQuestionDTO> list = savedCourseQuestions.stream().map(cq -> cq.ofWithOutTag()).collect(Collectors.toList());
        Course savedCourse = courseService.getCourse(course_id);
        CourseDTO courseDTO = savedCourse.of();

        model.addAttribute("course", courseDTO);
        model.addAttribute("list", list);
        return "information/courseQuestion/question";
    }

    @PostMapping(value = "/add")
    @PreAuthorize("isAuthenticated()")
    public String inputCourseQuestion(@ModelAttribute CourseQuestion courseQuestion,
                                      @AuthenticationPrincipal Member member,
                                      @RequestParam(value = "course_id") Long course_id){
        courseQuestionService.saveCourseQuestion(courseQuestion, member, course_id);
        return "redirect:/question/select/"+course_id;
    }
}
