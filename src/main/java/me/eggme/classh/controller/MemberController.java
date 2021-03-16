package me.eggme.classh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import me.eggme.classh.dto.LoginResponseDTO;
import me.eggme.classh.dto.ResponseDataCode;
import me.eggme.classh.dto.ResponseStatusCode;
import me.eggme.classh.entity.Member;
import me.eggme.classh.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@Log4j2
public class MemberController {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private MemberService service;

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
    public Member loadUserData(String username) throws JsonProcessingException {
        return service.loadUser(username);
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

    @RequestMapping(value = "/errors")
    @ResponseBody
    public String errorPage(Exception e){
        return e.getMessage();
    }

}
