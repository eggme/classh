package me.eggme.classh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.CourseDTO;
import me.eggme.classh.domain.dto.CourseNoticeDTO;
import me.eggme.classh.domain.entity.Course;
import me.eggme.classh.domain.entity.CourseNotice;
import me.eggme.classh.domain.entity.Member;
import me.eggme.classh.service.CourseService;
import me.eggme.classh.service.CourseNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/notice")
@Slf4j
public class NoticeController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseNoticeService courseNoticeService;
    private ObjectMapper mapper = new ObjectMapper();


    /***
     * 강의와, 새소식 조회
     * @param url 조회할 강의의 url
     * @param model
     * @return
     */
    @GetMapping(value = "/{url}")
    public String courseNewly(@PathVariable String url, Model model){
        Course course = courseService.getCourse(url);
        CourseDTO courseDTO = course.of();
        List<CourseNotice> list = courseNoticeService.getCourseNoticeList(course.getId());
        log.info("새소식 " +list.size()+" 개");
        model.addAttribute("course", courseDTO);
        model.addAttribute("list", list);
        return "information/courseNewly/newly";
    }

    /***
     * 강사가 공지사항(새소식)을 올림
     * @param courseNotice 새소식 정보
     * @param member 강사 정보
     * @param course_id 새소식이 올라갈 강의 pk
     * @return 강의의 url
     * @throws JsonProcessingException ObjectMapper Exception
     */
    @PostMapping(value = "/add")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public String addNotice(@ModelAttribute CourseNotice courseNotice,
                            @RequestParam("isPublic") boolean isPublic,
                            @AuthenticationPrincipal Member member,
                            @RequestParam(value = "course_id") Long course_id) throws JsonProcessingException {
        courseNotice.setPublic(isPublic);
        String url = courseNoticeService.addNotice(courseNotice, member, course_id);

        Map<String, String> map = new HashMap<>();
        map.put("url", url);
        return mapper.writeValueAsString(map);
    }

    /***
     * 새소식을 수정할 때 요청되어 id를 기반으로 새소식을 조회한 후 json 으로 데이터 전송
     * @param id 새소식 pk
     * @return
     */
    @PostMapping(value = "/select")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public CourseNoticeDTO selectNotice(@RequestParam(value = "notice_id") Long id){
        CourseNotice courseNotice = courseNoticeService.selectNotice(id);
        CourseNoticeDTO courseNoticeDTO = courseNotice.of();
        return courseNoticeDTO;
    }

    @PostMapping(value = "/edit")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public String editNotice(@ModelAttribute CourseNotice courseNotice,
                             @RequestParam("isPublic") boolean isPublic) throws JsonProcessingException {
        courseNotice.setPublic(isPublic);
        log.info(courseNotice.toString() + " : " +isPublic);
        String url = courseNoticeService.editNotice(courseNotice);

        Map<String, String> map = new HashMap<>();
        map.put("url", url);

        return mapper.writeValueAsString(map);
    }

    @PostMapping(value = "/delete")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public String deleteNotice(@RequestParam(value = "notice_id") Long id) throws JsonProcessingException {
        String url = courseNoticeService.deleteNotice(id);
        Map<String, String> map = new HashMap<>();
        map.put("url", url);
        return mapper.writeValueAsString(map);
    }

}
