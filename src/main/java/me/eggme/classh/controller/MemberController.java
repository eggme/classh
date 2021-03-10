package me.eggme.classh.controller;

import me.eggme.classh.entity.Member;
import me.eggme.classh.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class MemberController {

    @Autowired
    ApplicationContext context;

    @Autowired
    private MemberService service;

    @RequestMapping(value = "/")
    public String index(){
        return "root/index";
    }

    @RequestMapping(value = "/login")
    public String login(){
        return "root/login";
    }

    @RequestMapping(value = "/user/logout")
    public String home(){
        return "redirect:/";
    }

    @GetMapping(value = "/signUp")
    public String signUp(){
        return "root/signUp";
    }

    @PostMapping(value = "/signUp")
    public String signUpUser(@ModelAttribute Member member,
                             BindingResult bindingResult){
        System.out.println("signUpUser Handler!");
        bindingResult.getAllErrors().forEach(e->
                        System.out.println(e.toString())
        );
        service.save(member);
        return "root/index";
    }

    @RequestMapping(value = "/user/login?error")
    public void loginErrorBy(@RequestParam(value = "ERROR_MSG") String msg,
                               HttpServletResponse response){
        Environment environment = context.getEnvironment();
        try (PrintWriter writer = response.getWriter()) {
            String errorMsg = environment.getProperty(msg);
            writer.println("<script>alert(" + errorMsg + "); history.go(-1);</script>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/errors")
    @ResponseBody
    public String errorPage(Exception e){
        return e.getMessage();
    }

}
