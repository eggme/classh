package me.eggme.classh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.LoginResponseDTO;
import me.eggme.classh.domain.dto.MemberDTO;
import me.eggme.classh.domain.dto.ResponseDataCode;
import me.eggme.classh.domain.dto.ResponseStatusCode;
import me.eggme.classh.domain.entity.Member;
import me.eggme.classh.service.MemberService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class MemberController {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private MemberService service;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/")
    public String index(){
        return "root/index";
    }

    @GetMapping(value = "/login")
    public String login(){
        return "root/login";
    }

    @GetMapping(value = "/signUp")
    public String signUp(){
        return "root/signUp";
    }

    @PostMapping(value = "/signUp")
    public String signUpUser(@ModelAttribute Member member){
        service.save(member);
        return "redirect:/";
    }

    @PostMapping(value = "/userData")
    @ResponseBody
    public MemberDTO loadUserData(String username) throws JsonProcessingException {
        Member member = service.loadUser(username);
        MemberDTO dto = member.of();
        return dto;
    }

    @RequestMapping(value = "/login/error")
    @ResponseBody
    public String loginErrorBy(HttpServletRequest request) throws JsonProcessingException {
        Environment environment = applicationContext.getEnvironment();
        String errorMsg = environment.getProperty(request.getAttribute("ERROR_MSG").toString());
        ObjectMapper mapper = new ObjectMapper();

        LoginResponseDTO dto = new LoginResponseDTO();
        dto.setCode(ResponseDataCode.ERROR);
        dto.setStatus(ResponseStatusCode.ERROR);
        dto.setMessage(errorMsg);

        String result = mapper.writeValueAsString(dto);
        return result;
    }

    @RequestMapping(value = "/test")
    public String test(){
        return "root/test";
    }


    @RequestMapping(value = "/errors")
    @ResponseBody
    public String errorPage(Exception e){
        return e.getMessage();
    }

}
