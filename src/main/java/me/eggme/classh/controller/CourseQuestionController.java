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
import me.eggme.classh.service.MemberService;
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
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/question")
@Slf4j
public class CourseQuestionController {

    @Autowired
    private MemberService memberService;
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
        Set<CourseCommentDTO> courseCommentDTOList = courseQuestionService.selectCourseComment(savedCourseQuestion);
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
    public String selectCourseQuestion(Model model, @PathVariable(value = "id") Long course_id,
                                       @AuthenticationPrincipal Member member){

        if(member != null){
            MemberHistoryDTO memberHistoryDTO = memberService.getMemberHistory(member.getId());
            model.addAttribute("courseHistory", memberHistoryDTO); // 수강관련
        }

        Set<CourseQuestion> savedCourseQuestions = courseQuestionService.selectCourseQuestions(course_id);
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

    /**
     * 수업 페이지에서  ajax 방식으로 특정 강의에 질문을 추가
     * @param id 강의 pk
     * @param class_id 수업 pk
     * @param title 질문 제목
     * @param tags 질문 태그들
     * @param content 질문 내용
     * @param member 작성자
     * @return
     */
    @PostMapping(value = "/add/json")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public String addCourseQuestionForStudyRoom(@RequestParam(value = "id") Long id,
                                                @RequestParam(value = "class_id") Long class_id,
                                                @RequestParam(value = "title") String title,
                                                @RequestParam(value = "tags[]") String[] tags,
                                                @RequestParam(value = "content") String content,
                                                @AuthenticationPrincipal Member member) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        CourseQuestion savedCourseQuestion = courseQuestionService.saveCourseQuestion(id, class_id, title, tags, content, member);
        CourseQuestionDTO courseQuestionDTO = savedCourseQuestion.of();
        return objectMapper.writeValueAsString(courseQuestionDTO);
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
    public CourseQuestionDTO getCourseQuestionJson(@RequestParam(value = "id") Long id) {
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
        courseQuestionService.deleteCourseQuestion(id);
        Map<String, Long> map = new HashMap<>();
        map.put("id", id);
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
     * @param content 댓글 내용
     * @return
     */
    @PostMapping(value = "/comment/add/comment")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public String addReply(@RequestParam(value = "comment_id") Long id,
                                            @RequestParam(value = "question_id") Long question_id,
                                            @AuthenticationPrincipal Member member,
                                            @RequestParam(value = "commentContent") String content) throws JsonProcessingException {
        courseQuestionService.addReply(id,  member, content);
        /* 여기서 부터 작성하면 됩니다 */
        Map<String, Long> map = new HashMap<>();
        map.put("id", question_id);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }

    /***
     * 질문 게시글에 답변/댓글을 삭제함
     * @param id 삭제할 답변/댓글 pk
     * @return
     */
    @PostMapping(value = "/comment/delete")
    @PreAuthorize("isAuthenticated()")
    public String deleteComment(@RequestParam(value = "id") Long id){
        log.info("삭제할 값 : " + id);
        Long redirect_id = courseQuestionService.deleteCourseComment(id);
        return "redirect:/question/"+redirect_id;
    }

    /***
     * 답변 수정 클릭 시 id를 가지고 답변의 정보를 조회 후 json으로 출력
     * @param id
     * @return
     */
    @PostMapping(value = "/select/comment")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public CourseCommentDTO selectCourseComment(@RequestParam(value = "id") Long id){
        CourseComment savedCourseComment = courseQuestionService.getCourseComment(id);
        CourseCommentDTO courseCommentDTO = savedCourseComment.of();
        return courseCommentDTO;
    }

    /****
     * 답변/댓글 정보를 수정할 때 실행되는 콜백 
     * @param id 답변/댓글 pk
     * @param content 수정될 답변/댓글 데이터
     * @return
     */
    @PostMapping(value = "/comment/edit")
    @PreAuthorize("isAuthenticated()")
    public String editCourseComment(@RequestParam(value="id") Long id,
                                    @RequestParam(value = "commentContent") String content){
        log.info(content);
        Long redirect_id = courseQuestionService.editCourseComment(id, content);
        return "redirect:/question/"+redirect_id;
    }
}
