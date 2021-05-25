package me.eggme.classh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.CourseDTO;
import me.eggme.classh.domain.dto.MemberDTO;
import me.eggme.classh.domain.entity.Member;
import me.eggme.classh.service.CourseService;
import me.eggme.classh.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
public class MemberController {

    @Autowired
    private Environment environment;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CourseService courseService;

    @GetMapping(value = "/")
    public String index(Model model){
        log.info("메인페이지이빈다.");
        List<CourseDTO> courseList = courseService.getCourseList();
        log.info("강의 수 = "+courseList.size());
        model.addAttribute("list", courseList);
        return "root/index";
    }

    @GetMapping(value = "/login*")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model){
        if(exception != null) {
            model.addAttribute("error", error);
            model.addAttribute("exception", environment.getProperty(exception));
        }
        return "root/login";
    }

    @GetMapping(value = "/signUp")
    public String signUp(){
        return "root/signUp";
    }

    @PostMapping(value = "/signUp")
    public String signUpUser(@ModelAttribute MemberDTO memberDTO){
        memberService.save(memberDTO);
        return "redirect:/";
    }

    @PostMapping(value = "/userData")
    @ResponseBody
    public MemberDTO loadUserData(@AuthenticationPrincipal Member member) throws JsonProcessingException {
        Member loadMember = memberService.loadUser(member.getUsername());
        MemberDTO dto = loadMember.of();
        return dto;
    }

    @GetMapping(value = "/denied")
    public String accessDenied(@RequestParam(value = "exception", required = false) String exception, Model model){
        model.addAttribute("exception", exception);
        return "access_denied";
    }

    @RequestMapping(value = "/errors")
    @ResponseBody
    public String errorPage(Exception e){
        return e.getMessage();
    }


    @GetMapping(value = "/addInstructor")
    @PreAuthorize("isAuthenticated()")
    public String addInstructorPage(){
        return "inst/addInstructor";
    }

    @PostMapping(value = "/add/instructor")
    public String addInstructor(@AuthenticationPrincipal Member member,
                                @ModelAttribute Member additionalMemberData){
        log.info(member.toString());
        log.info(additionalMemberData.toString());
        memberService.createInstructor(member, additionalMemberData);
        return "redirect:/";
    }
}
