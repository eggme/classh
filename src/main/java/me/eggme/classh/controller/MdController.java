package me.eggme.classh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.*;
import me.eggme.classh.domain.entity.Course;
import me.eggme.classh.domain.entity.Instructor;
import me.eggme.classh.domain.entity.Member;
import me.eggme.classh.domain.entity.Notification;
import me.eggme.classh.service.CourseService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/md")
@Secured("ROLE_MD")
@Slf4j
public class MdController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private MemberService memberService;

    private static EnumSet notificationTypes = EnumSet.allOf(NotificationType.class);

    @GetMapping(value = "/dashboard")
    public String mdDashboard(@AuthenticationPrincipal Member member, Model model){

        List<CourseDTO> submitList = courseService.getSubmitCourses();
        model.addAttribute("submitList", submitList);


        model.addAttribute("member", member);
        return "md/dashboard";
    }

    @GetMapping(value = "/course/list")
    public String mdCourseList(@PageableDefault(size = 8, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                               Model model){
        Page<Course> list = courseService.getCourses(pageable);
        model.addAttribute("list", list.getContent());
        model.addAttribute("current", list.getNumber());
        model.addAttribute("total", list.getTotalPages());
        return "md/menu/courseList";
    }

    @GetMapping(value = "/instructor/list")
    public String mdInstructorList(@PageableDefault(size = 8, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                   Model model){
        Page<Instructor> list = memberService.getInstructors(pageable);
        model.addAttribute("list", list.getContent());
        model.addAttribute("current" , list.getNumber());
        model.addAttribute("total", list.getTotalPages());
        return "md/menu/instructorList";
    }

    @GetMapping(value = "/instructor/info/{id}")
    public String instructorInfo(@PathVariable Long id, Model model){
        Member member = memberService.getMember(id);
        MemberDTO memberDTO = member.of();
        model.addAttribute("member", memberDTO);
        model.addAttribute("notifications", notificationTypes);

        /* 모달 관련 */
        String modal = model.asMap().get("modal") == null ? null : model.asMap().get("modal").toString();
        if(modal != null) model.addAttribute("modal", modal);

        return "md/menu/instructorInfo";
    }

    @GetMapping(value = "/course/info/{id}")
    public String courseInfo(@PathVariable Long id, Model model){
        Course course = courseService.getCourse(id);
        CourseDTO courseDTO = course.of();
        model.addAttribute("course", courseDTO);
        return "md/menu/courseInfo";
    }

    @PostMapping(value = "/course/change")
    @ResponseBody
    public String changeCourseState(@AuthenticationPrincipal Member md,
                                    @RequestParam Long id,
                                    @RequestParam CourseState courseState) throws JsonProcessingException {
        log.info(courseState.getValue());
        courseService.changeCourseState(md, id, courseState);

        Map<String, String> map = new HashMap<>();
        map.put("result", "success");

        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(map);
    }

    @PostMapping(value = "/add/notification")
    public String addNotification(@AuthenticationPrincipal Member md,
                                  @RequestParam(value = "member_id") Long id,
                                  RedirectAttributes redirectAttributes,
                                  @ModelAttribute Notification notification){
        memberService.writeNotification(md, id, notification);
        redirectAttributes.addFlashAttribute("modal", "success");
        return "redirect:/md/instructor/info/"+id;
    }
}
