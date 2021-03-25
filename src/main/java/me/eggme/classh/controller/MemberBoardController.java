package me.eggme.classh.controller;

import lombok.extern.log4j.Log4j2;
import me.eggme.classh.dto.MemberDTO;
import me.eggme.classh.entity.Member;
import me.eggme.classh.service.MemberBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping(value = "/member")
@Log4j2
public class MemberBoardController {

    @Autowired
    private MemberBoardService memberBoardService;

    @GetMapping(value = "/dashboard")
    public String dashboardView(HttpSession session, Model model){
        String email = session.getAttribute("username").toString();
        Member member = memberBoardService.loadMember(email);
        MemberDTO memberDTO = member.of();
        model.addAttribute("member", memberDTO);
        return "board/dashboard";
    }

    @GetMapping(value = "/profile")
    public String profileView(HttpSession session, Model model){
        String email = session.getAttribute("username").toString();
        Member member = memberBoardService.loadMember(email);
        log.info(email + " : " +member.toString());
        MemberDTO memberDTO = member.of();
        model.addAttribute("member", memberDTO);
        return "board/profile";
    }

    @PostMapping(value = "/modify/password")
    public String modifyUserPassword(@RequestParam(value = "current_pw") String current_pw,
                                     @RequestParam(value = "new_pw") String new_pw,
                                     HttpSession session){
        String email = session.getAttribute("username").toString();
        memberBoardService.changePassword(current_pw, new_pw, email);
        return "redirect:/member/profile";
    }

    @PostMapping(value = "/modify/name")
    public String modifyUserName(@RequestParam(value = "name") String name, HttpSession session){
        String email = session.getAttribute("username").toString();
        memberBoardService.changeName(name, email);
        return "redirect:/member/profile";
    }

    @PostMapping(value = "/modify/image")
    public String modifyProfileImage(@RequestParam(value = "image") MultipartFile multipartFile,
                                     HttpSession session,
                                     HttpServletRequest request) throws Exception {
        String email = session.getAttribute("username").toString();
        String realPath = request.getRealPath("/imgs/upload");
        File file = new File(realPath+"\\"+multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        memberBoardService.changeProfile(file, email);
        return "redirect:/member/profile";
    }

    @PostMapping(value = "/modify/self")
    public String modifySelfIntroduce(@RequestParam(value = "self") String self,
                                      HttpSession session){
        String email = session.getAttribute("username").toString();
        memberBoardService.changeSelfIntroduce(self, email);
        return "redirect:/member/profile";
    }
}
