package me.eggme.classh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.CourseCommentDTO;
import me.eggme.classh.domain.dto.CourseDTO;
import me.eggme.classh.domain.dto.CourseNoticeDTO;
import me.eggme.classh.domain.dto.MemberHistoryDTO;
import me.eggme.classh.domain.entity.Course;
import me.eggme.classh.domain.entity.CourseComment;
import me.eggme.classh.domain.entity.CourseNotice;
import me.eggme.classh.domain.entity.Member;
import me.eggme.classh.service.CourseService;
import me.eggme.classh.service.CourseNoticeService;
import me.eggme.classh.service.MemberService;
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
    private MemberService memberService;
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
    public String courseNewly(@PathVariable String url, Model model, @AuthenticationPrincipal Member member){

        if(member != null){
            MemberHistoryDTO memberHistoryDTO = memberService.getMemberHistory(member.getId());
            model.addAttribute("courseHistory", memberHistoryDTO); // 수강관련
        }

        Course course = courseService.getCourse(url);
        CourseDTO courseDTO = course.of();
        List<CourseNoticeDTO> list = courseNoticeService.getCourseNoticeList(course.getId());
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

    /***
     * 새소식을 수정하는 과정 중 최종적으로 바꾼 데이터를 반영시키는 작업을 함
     * @param courseNotice 바뀐 새소식
     * @param isPublic 공개범위
     * @return 강의 url
     * @throws JsonProcessingException
     */
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

    /***
     * 새소식을 삭제하는 함수
     * @param id 삭제할 새소식 pk
     * @return 강의 url
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/delete")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public String deleteNotice(@RequestParam(value = "notice_id") Long id) throws JsonProcessingException {
        String url = courseNoticeService.deleteNotice(id);
        Map<String, String> map = new HashMap<>();
        map.put("url", url);
        return mapper.writeValueAsString(map);
    }

    /***
     * 새소식의 댓글을 추가할 때 호출
     * @param id 추가될 새소식의 pk
     * @param member 추가할 사람
     * @param content 추가될 댓글의 내용
     * @return
     */
    @PostMapping(value = "/add/comment")
    public String addCommentForNotice(@RequestParam(value = "notice_id") Long id,
                                      @AuthenticationPrincipal Member member,
                                      @RequestParam(value = "commentContent") String content){
        String url = courseNoticeService.addCommentForNotice(id, member, content);
        return "redirect:/notice/"+url;
    }

    /***
     * 새소식의 댓글을 삭제할 때 호출
     * @param id 삭제할 댓글 pk
     * @return
     */
    @PostMapping(value = "/delete/comment")
    @ResponseBody
    public String deleteCommentForNotice(@RequestParam(value = "comment_id") Long id){
        courseNoticeService.deleteCommentForNotice(id);
        return "success";
    }

    /***
     *  새소식의 댓글을 수정할 때 호출
     * @param id 수정할 댓글 pk
     * @param content 수정될 댓글 내용
     * @return 수정된 댓글 json
     */
    @PostMapping(value = "/edit/comment")
    @ResponseBody
    public CourseCommentDTO editCommentForNotice(@RequestParam(value = "comment_id") Long id,
                                       @RequestParam(value = "commentContent") String content){
        CourseComment courseComment = courseNoticeService.editCommentForNotice(id, content);
        CourseCommentDTO courseCommentDTO = courseComment.of();
        return courseCommentDTO;
    }
}
