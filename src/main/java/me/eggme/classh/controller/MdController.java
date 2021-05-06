package me.eggme.classh.controller;

import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.entity.Member;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/md")
@Secured("ROLE_MD")
@Slf4j
public class MdController {

    @GetMapping(value = "/dashboard")
    public String mdDashboard(@AuthenticationPrincipal Member member, Model model){
        model.addAttribute("member", member);
        return "md/dashboard";
    }
}
