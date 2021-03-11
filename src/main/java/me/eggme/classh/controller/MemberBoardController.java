package me.eggme.classh.controller;

import me.eggme.classh.service.MemberBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/member")
public class MemberBoardController {

    @Autowired
    private MemberBoardService memberBoardService;

    @GetMapping(value = "/dashboard")
    public String dashboardView(){
        return "board/dashboard";
    }

    @GetMapping(value = "/profile")
    public String profileView(){
        return "board/profile";
    }

    @PostMapping(value = "/modify/password")
    public String modifyUserPassword(@RequestParam(value = "current_pw") String current_pw,
                                     @RequestParam(value = "new_pw") String new_pw,
                                     @RequestParam(value = "user_token") String email){
        memberBoardService.changePassword(current_pw, new_pw, email);
        return "board/profile";
    }

    @PostMapping(value = "/modify/name")
    public String modifyUserName(@RequestParam(value = "name") String name,
                                 @RequestParam(value = "user_token") String email){
        memberBoardService.changeName(name, email);
        return "board/profile";
    }
}
