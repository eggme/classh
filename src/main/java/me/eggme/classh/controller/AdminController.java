package me.eggme.classh.controller;

import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.NotificationType;
import me.eggme.classh.domain.entity.Member;
import me.eggme.classh.domain.entity.Notification;
import me.eggme.classh.repository.MemberRepository;
import me.eggme.classh.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.EnumSet;

@Controller
@RequestMapping("/admin")
@Secured("ROLE_ADMIN")
@Slf4j
public class AdminController {

    @Autowired private MemberService memberService;

    @GetMapping(value = "/dashboard")
    public String adminDashboard(@AuthenticationPrincipal Member member, Model model){
        model.addAttribute("member", member);
        return "admin/dashboard";
    }

    @GetMapping(value = "/members")
    public String getUserList(@PageableDefault(size = 8, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                              Model model){

        Page<Member> pages = memberService.getAllUserList(pageable);
        model.addAttribute("current", pages.getNumber());
        model.addAttribute("list", pages.getContent());
        model.addAttribute("total", pages.getTotalPages());

        return "admin/memberList";
    }

    @GetMapping(value = "/notices")
    public String getNotices(@PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                             Model model){
        Page<Notification> pages = memberService.getAllNotifications(pageable);
        model.addAttribute("current", pages.getNumber());
        model.addAttribute("list", pages.getContent());
        model.addAttribute("total", pages.getTotalPages());

        /* 모달 관련 */
        String modal = model.asMap().get("modal") == null ? null : model.asMap().get("modal").toString();
        if(modal != null) model.addAttribute("modal", modal);

        return "admin/noticeList";
    }

    @PostMapping(value = "/add/notice")
    public String addNotification(@AuthenticationPrincipal Member md,
                                  RedirectAttributes redirectAttributes,
                                  @ModelAttribute Notification notification){
        memberService.writeSystemNotification(md, notification);
        redirectAttributes.addFlashAttribute("modal", "System");
        return "redirect:/admin/notices";
    }
}
