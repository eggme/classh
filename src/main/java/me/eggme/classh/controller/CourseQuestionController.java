package me.eggme.classh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.*;
import me.eggme.classh.domain.entity.Course;
import me.eggme.classh.domain.entity.CourseComment;
import me.eggme.classh.domain.entity.CourseQuestion;
import me.eggme.classh.domain.entity.Member;
import me.eggme.classh.service.CourseQuestionService;
import me.eggme.classh.service.CourseService;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/question")
@Slf4j
public class CourseQuestionController {

    @Autowired
    private CourseQuestionService courseQuestionService;
    @Autowired
    private CourseService courseService;

    /***
     * 질문에 등록된 정보를 조회 (답변, 강의, 수업 등)
     * @param id 질문 pk
     * @param model
     * @return
     */
    @GetMapping(value = "/{id}")
    public String getCourseQuestion(@PathVariable Long id, Model model){
        CourseQuestion savedCourseQuestion = courseQuestionService.getCourseQuestion(id);
        List<CourseCommentDTO> courseCommentDTOList = courseQuestionService.selectCourseComment(savedCourseQuestion);
        CourseQuestionDTO courseQuestionDTO = savedCourseQuestion.of();
        CourseDTO courseDTO = courseQuestionDTO.getCourse().of();
        MemberDTO memberDTO = courseQuestionDTO.getMember().of();
        model.addAttribute("courseQuestion", courseQuestionDTO);
        model.addAttribute("course", courseDTO);
        model.addAttribute("member", memberDTO);
        model.addAttribute("list", courseCommentDTOList);

        if(courseQuestionDTO.getCourseClass() != null){
            CourseClassDTO courseClassDTO = courseQuestionDTO.getCourseClass().of();
            model.addAttribute("courseClass", courseClassDTO);
        }

        return "questions/courseQnA";
    }

    /***
     * 해당 강의에 등록된 질문을 검색
     * @param model
     * @param course_id 강의 pk
     * @return
     */
    @GetMapping(value = "/select/{id}")
    public String selectCourseQuestion(Model model, @PathVariable(value = "id") Long course_id){

        List<CourseQuestion> savedCourseQuestions = courseQuestionService.selectCourseQuestions(course_id);
        List<CourseQuestionDTO> list = savedCourseQuestions.stream().map(cq -> cq.ofWithOutTag()).collect(Collectors.toList());
        Course savedCourse = courseService.getCourse(course_id);
        CourseDTO courseDTO = savedCourse.of();

        model.addAttribute("course", courseDTO);
        model.addAttribute("list", list);
        return "information/courseQuestion/question";
    }

    /***
     * 질문을 작성하는 메소드
     * @param courseQuestion 작성 할 질문 객체
     * @param member 질문을 작성하는 유저
     * @param course_id 질문이 등록될 강의
     * @return
     */
    @PostMapping(value = "/add")
    @PreAuthorize("isAuthenticated()")
    public String inputCourseQuestion(@ModelAttribute CourseQuestion courseQuestion,
                                      @AuthenticationPrincipal Member member,
                                      @RequestParam(value = "course_id") Long course_id){
        courseQuestionService.saveCourseQuestion(courseQuestion, member, course_id);
        return "redirect:/question/select/"+course_id;
    }

    /***
     * 질문에 답변을 입력하는 메소드
     * @param question_id 질문 pk
     * @param member 답변을 올리는 유저
     * @param commentContent 답변 내용
     * @param model
     * @return
     */
    @PostMapping(value = "/add/comment")
    @PreAuthorize("isAuthenticated()")
    public String addCourseComment(@RequestParam(value = "q_id") Long question_id,
                                   @AuthenticationPrincipal Member member,
                                   @RequestParam(value = "commentContent") String commentContent,
                                   Model model){
        log.info("질문 pk : " + question_id);
        log.info("댓글 작성자 : " + member.toString());
        log.info("댓글 정보 : " + commentContent);
        courseQuestionService.addCourseComment(question_id, member, commentContent);
        return "redirect:/question/"+question_id;
    }

    /***
     * 질문 수정 폼을 만들 때 id를 기반으로 검색해서 json으로 뿌려줌
     * @param id 질문 pk
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/select")
    @ResponseBody
    public CourseQuestionDTO getCourseQuestionJson(@RequestParam(value = "id") Long id) throws JsonProcessingException {
        CourseQuestion savedCourseQuestion = courseQuestionService.getCourseQuestion(id);
        CourseQuestionDTO courseQuestionDTO = savedCourseQuestion.of();
        return courseQuestionDTO;
    }

    /***
     * 질문을 올린 사람이 질문을 수정할 때 요청
     * @param courseQuestion 수정된 질문 내용
     * @param model
     * @return
     */
    @PostMapping(value = "/edit")
    public String editCourseQuestion(@ModelAttribute CourseQuestion courseQuestion, Model model){
        CourseQuestion savedCourseQuestion = courseQuestionService.editCourseQuestion(courseQuestion);
        log.info(savedCourseQuestion.toString());
        return "redirect:/question/"+savedCourseQuestion.getId();
    }

    /***
     * 질문을 삭제할 시 질문리스트로 이동
     * @param id 삭제할 질문 pk
     * @return
     */
    @PostMapping(value = "/delete")
    @ResponseBody
    public String deleteCourseQuestion(@RequestParam(value = "id") Long id) throws JsonProcessingException {
        String url = courseQuestionService.deleteCourseQuestion(id);
        Map<String, String> map = new HashMap<>();
        map.put("url", url);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(map);
    }

    /***
     * 사용자가 올린 질문의 상태를 바꿈 (미해결 -> 해결, 해결 -> 미해결)
     * @param id 상태를 바꿀 질문의 pk
     * @param status 상태 정보
     * @return
     */
    @PostMapping(value = "/changeStatus")
    @ResponseBody
    public String changeCourseQuestionStatus(@RequestParam(value = "id") Long id,
                                             @RequestParam(value = "status") String status){
        courseQuestionService.changeCourseQuestionStatus(id, status);
        return "success";
    }

    /***
     * 질문에 달린 답변에 댓글을 입력
     * @param id 답변 pk
     * @param member 댓글을 입력하는 주체
     * @param conent 댓글 내용
     * @return
     */
    @PostMapping(value = "/comment/add/comment")
    @PreAuthorize("isAuthenticated()")
    public String addCourseCommentInComment(@RequestParam(value = "comment_id") Long id,
                                            @AuthenticationPrincipal Member member,
                                            @RequestParam(value = "commentContent") String conent){
        /* 여기서 부터 작성하면 됩니다 */
        return "redirect:/question/"+id;
    }
}
