package me.eggme.classh.controller;

import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.CourseDTO;
import me.eggme.classh.domain.dto.MemberDTO;
import me.eggme.classh.domain.dto.MemberHistoryDTO;
import me.eggme.classh.domain.entity.Course;
import me.eggme.classh.domain.entity.Member;
import me.eggme.classh.domain.entity.SignUpCourse;
import me.eggme.classh.service.MemberBoardService;
import me.eggme.classh.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/member")
@PreAuthorize("isAuthenticated()")
@Slf4j
public class MemberBoardController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberBoardService memberBoardService;

    @GetMapping(value = "/dashboard")
    public String dashboardView(@AuthenticationPrincipal Member member, Model model){
        Member loadMember = memberBoardService.loadMember(member.getUsername());
        MemberDTO memberDTO = loadMember.of();
        model.addAttribute("member", memberDTO);
        return "board/dashboard";
    }

    @GetMapping(value = "/profile")
    public String profileView(@AuthenticationPrincipal Member member, Model model){
        Member loadMember = memberBoardService.loadMember(member.getUsername());
        MemberDTO memberDTO = loadMember.of();
        model.addAttribute("member", memberDTO);
        return "board/profile";
    }

    @GetMapping(value = "/list")
    public String getMyCourseList(@AuthenticationPrincipal Member member, Model model){

        MemberHistoryDTO memberHistoryDTO = memberService.getMemberHistory(member.getId());
        model.addAttribute("courseHistory", memberHistoryDTO); // 수강관련

        Set<Course> courseSet = memberBoardService.getCourseSet(member);
        Set<CourseDTO> courseDTOSet = courseSet.stream().map(c -> c.of()).collect(Collectors.toSet());
        model.addAttribute("list", courseDTOSet);

        return "board/courseList";
    }

    @PostMapping(value = "/modify/password")
    public String modifyUserPassword(@RequestParam(value = "current_pw") String current_pw,
                                     @RequestParam(value = "new_pw") String new_pw,
                                     @AuthenticationPrincipal Member member){
        memberBoardService.changePassword(current_pw, new_pw, member.getUsername());
        return "redirect:/member/profile";
    }

    @PostMapping(value = "/modify/name")
    public String modifyNickName(@RequestParam(value = "name") String name, @AuthenticationPrincipal Member member){
        memberBoardService.changeName(name, member.getUsername());
        return "redirect:/member/profile";
    }

    @PostMapping(value = "/modify/image")
    public String modifyProfileImage(@RequestParam(value = "image") MultipartFile multipartFile,
                                     @AuthenticationPrincipal Member member,
                                     HttpServletRequest request) throws Exception {
        String realPath = request.getRealPath("/imgs/upload");
        File file = new File(realPath+"\\"+multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        memberBoardService.changeProfile(file, member.getUsername());
        return "redirect:/member/profile";
    }

    @PostMapping(value = "/modify/self")
    public String modifySelfIntroduce(@RequestParam(value = "self") String self, @AuthenticationPrincipal Member member){
        memberBoardService.changeSelfIntroduce(self,  member.getUsername());
        return "redirect:/member/profile";
    }
}
