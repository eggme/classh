package me.eggme.classh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.*;
import me.eggme.classh.domain.entity.Course;
import me.eggme.classh.domain.entity.CourseQuestion;
import me.eggme.classh.domain.entity.Member;
import me.eggme.classh.domain.entity.Notification;
import me.eggme.classh.service.CourseQuestionService;
import me.eggme.classh.service.MemberBoardService;
import me.eggme.classh.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
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
    @Autowired
    private CourseQuestionService courseQuestionService;

    @GetMapping(value = "/dashboard")
    public String dashboardView(@AuthenticationPrincipal Member member, Model model){
        Member loadMember = memberBoardService.loadMember(member.getUsername());
        MemberDTO memberDTO = loadMember.of();
        MemberHistoryDTO memberHistoryDTO = loadMember.ofHistory();
        model.addAttribute("member", memberDTO);
        model.addAttribute("history", memberHistoryDTO);
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

        List<Course> courseSet = memberBoardService.getCourseList(member);
        List<CourseDTO> courseDTOSet = courseSet.stream().map(c -> c.of()).collect(Collectors.toList());
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

    /***
     *  ajax로 헤더 알림 아이콘에 마우스 hover 시 ajax 요청, 사용자의 모든 알림을 불러옴
     * @param member
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/get/notification")
    @ResponseBody
    public String getNotifications(@AuthenticationPrincipal Member member) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<NotificationDTO> list = memberService.getNotificationTop6(member);
        String value = mapper.writeValueAsString(list);
        return value;
    }

    /**
     * 내 알림 페이지에서 클릭 시 알림 조회
     * @param id 알림 pk
     * @param model
     * @return
     */
    @GetMapping(value = "/notification/{id}")
    public String getNotification(@PathVariable Long id, Model model){
        Notification notification = memberService.getNotification(id);
        NotificationDTO notificationDTO = notification.of();
        model.addAttribute("notification", notificationDTO);
        return "community/communityBoard";
    }

    /**
     * 내 알림 페이지에서 나에게 발송된 모든 알림 조회
     * @param pageable 페이지 객체
     * @param member 유저
     * @param model
     * @return
     */
    @GetMapping(value = "/notifications")
    public String getNotificationList(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal Member member, Model model){
        Page<Notification> list = memberService.getNotifications(member, pageable);
        List<NotificationDTO> notificationDTOList = list.getContent().stream().map(n ->
                n.of()).collect(Collectors.toList());
        model.addAttribute("list", notificationDTOList);
        return "board/notification";
    }

    /**
     * 알림 클릭 시 해당 알림의 타입여부에 따라 다른 행동을 한 후 한번도 읽지 않은 알람이라면 읽음 처리를 함
     * @param id 알림 pk
     * @param notificationType 알림 타입
     * @param redirectAttributes 모달 전달을 위한 객체
     * @return
     */
    @GetMapping(value = "/notification/{id}/{type}")
    public String temp(@PathVariable(value = "id") Long id,
                       @PathVariable(value = "type")NotificationType notificationType,
                       RedirectAttributes redirectAttributes){
        Notification savedNotification = memberService.getNotification(id);
        String redirectURL = notificationType.getUrl();
        Long targetID = savedNotification.getTarget();

        /* 클릭한 알림이 읽지 않은 상태이면서 (임시로 타겟이 0이 아니면서로 바꿈), 강의가 승인될 때 나오는 강사알림 일 때 */
        if((targetID != null) && (notificationType == NotificationType.INSTRUCTOR_NOTICE)){
            redirectAttributes.addFlashAttribute("modal", "addHashTag");
        }
        /* 클릭한 알림이 읽지 않은 상태일 때 읽음 처리로 변경 */
        if(!savedNotification.isRead()){
            memberService.readNotification(savedNotification);
        }
        /* 만약 타겟이 없다면 시스템 공지사항으로 보냄 */
        if(targetID == null){
            redirectURL = "/member/notification";
            targetID = savedNotification.getId();
        }

        return "redirect:"+redirectURL+"/"+targetID;
    }

    /**
     * 내 질문을 눌렀을 때 로그인된 유저의 모든 질문을 조회
     * @param member 유저
     * @param model
     * @return
     */
    @GetMapping(value = "/questions")
    public String getMyQuestions(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                @AuthenticationPrincipal Member member,
                               Model model){
        Page<CourseQuestion> pages = courseQuestionService.getQuestionList(pageable,
                member);

        List<CourseQuestionDTO> list = pages.getContent().stream().map(cq ->
                cq.of()).collect(Collectors.toList());

        model.addAttribute("current", pages.getNumber());
        model.addAttribute("list", list);
        model.addAttribute("total", pages.getTotalPages());
        return "board/questionList";
    }


}
