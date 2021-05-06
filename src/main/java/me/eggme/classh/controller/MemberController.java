package me.eggme.classh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.MemberDTO;
import me.eggme.classh.domain.entity.Member;
import me.eggme.classh.service.MemberService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class MemberController {

    @Autowired
    private Environment environment;

    @Autowired
    private MemberService service;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/")
    public String index(){
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
        service.save(memberDTO);
        return "redirect:/";
    }

    @PostMapping(value = "/userData")
    @ResponseBody
    public MemberDTO loadUserData(@AuthenticationPrincipal Member member) throws JsonProcessingException {
        Member loadMember = service.loadUser(member.getUsername());
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

}
