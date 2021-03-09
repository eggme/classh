package me.eggme.classh.controller;

import me.eggme.classh.dto.UserDTO;
import me.eggme.classh.entity.User;
import me.eggme.classh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@SessionAttributes("user")
@Controller
public class MainController {

    @Autowired
    private UserService service;

    @RequestMapping(value = "/")
    public String index(){
        return "root/index";
    }

    @GetMapping(value = "/signUp")
    public String signUp(){
        return "root/signUp";
    }

    @PostMapping(value = "/signUp")
    public String signUp(@ModelAttribute UserDTO userDTO){
        service.signUp(userDTO.getEmail(), userDTO.getPassword());
        return "root/index";
    }

    @RequestMapping(value = "/signIn")
    public String signIn(@RequestParam User user,
                         Model model){

        User loginUser = service.login(user);
        if(loginUser != null){
            model.addAttribute("user_name",loginUser.getName());
            model.addAttribute("state", 1);
            model.addAttribute("user", loginUser);
        }else{
            model.addAttribute("state", 2);
        }
        return "root/index";
    }

}
