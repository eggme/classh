package me.eggme.classh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.*;
import me.eggme.classh.domain.entity.*;
import me.eggme.classh.service.CourseQuestionService;
import me.eggme.classh.service.CourseService;
import me.eggme.classh.service.MemberService;
import me.eggme.classh.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import retrofit2.http.Path;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/study")
@Slf4j
public class StudyController {

    @Autowired private CourseService courseService;
    @Autowired private StudyService studyService;
    @Autowired private MemberService memberService;
    @Autowired private CourseQuestionService courseQuestionService;

    /***
     * 강의 미리보기 클릭 시 미리보기 강의 유무 판별하여 제공
     * @param id 강의 pk
     * @param class_id 강의 영상 pk
     * @param model
     * @return
     */
    @GetMapping(value="/{id}/preview/{class_id}/**")
    public String coursePreview(@PathVariable(name = "id") Long id,
                                @PathVariable(name = "class_id") Long class_id,
                                @RequestParam(name = "tab", required = false) String tab,
                                Model model){
        Course course = courseService.getCourse(id);
        CourseClass courseClass = courseService.getCourseClass(class_id);
        CourseDTO courseDTO = course.of();
        CourseClassDTO courseClassDTO = courseClass.of();
        // 미리보기 강의가 맞을 경우
        if(courseService.isPreviewCourseClass(courseClass)){
            model.addAttribute("error", false);
        }else{
            // 미리보기 강의가 아닐 경우 (URL 조작)
            model.addAttribute("error", true);
        }

        /*  해당 수업의 질문 */
        List<CourseQuestion> courseQuestions = courseQuestionService.getQuestionList(class_id);

        model.addAttribute("course", courseDTO);
        model.addAttribute("courseClass", courseClassDTO);
        model.addAttribute("courseQuestions", courseQuestions);

        /* 모달 관련 */
        String modal = model.asMap().get("modal") == null ? null : model.asMap().get("modal").toString();
        if(modal != null) model.addAttribute("modal", modal);

        if(tab != null){
            model.addAttribute("tab", tab);
        }

        return "study/studyRoom/"+courseClassDTO.getName();
    }

    /***
     * 강의 정보에 이어학습하기를 눌렀을 때 실행
     * @param id 강의 pk
     * @param member 유저
     * @param model
     * @return
     */
    @GetMapping(value = "/{id}/lecture/")
    @PreAuthorize("isAuthenticated()")
    public String courseView(@PathVariable(name = "id") Long id,
                             @AuthenticationPrincipal Member member,
                             @RequestParam(name = "tab",  required = false) String tab,
                             Model model){
        String url = "";
        /* 해당 유저가 수강 관계를 맺고있는지 검사 */
        if(studyService.checkSignUpCourseMember(id, member.getId())){

            Course savedCourse = courseService.getCourse(id);
            Long courseClassId = 0L;

            if(member != null) { /* OSIV 때문에 직접 조회해서 줘야함 */
                MemberDTO memberDTO = (memberService.getMember(member.getId())).of();
                model.addAttribute("member", memberDTO);
            }

            if(studyService.hasCourseHistory(member.getId(), id)){ // 만약 하나라도 기록이 있다면

                CourseClass savedCourseClass = studyService.getLastCourseClass(member.getId(), id);
                courseClassId = savedCourseClass.getId();
                // 해당 강의의 기록 (비디오 재생에 쓰임, 어느 강의를 언제까지 봤나)
                CourseHistoryDTO courseHistory = studyService.getCourseHistory(member.getId(), savedCourseClass.getId());
                if(courseHistory != null){ // 해당 강의가 처음 볼 상황도 있음
                    model.addAttribute("currentHistory", courseHistory);
                }
                // 모든 강의의 기록 (목차에 쓰임, 어떤 강의를 어디까지 들었는지 확인하기 위해)
                List<CourseHistoryDTO> courseHistories = studyService.getCourseHistories(member.getId(), savedCourse.getId());
                model.addAttribute("courseHistories", courseHistories);

            }else{ // 기록이 아무것도 없을 때, 기본 첫번째 강의
                CourseClass courseClass = savedCourse.getCourseSections().stream().findFirst().get()
                        .getCourseClasses().stream().findFirst().get();
                courseClassId = courseClass.getId();
            }
            /*  해당 수업의 질문 */
            List<CourseQuestion> courseQuestions = courseQuestionService.getQuestionList(courseClassId);

            CourseClass savedCourseClass = courseService.getCourseClass(courseClassId);

            CourseDTO courseDTO = savedCourse.of();
            CourseClassDTO courseClassDTO = savedCourseClass.of();
            // 해당 유저의 강의 기록 (목차 수강률에 쓰임, 누가 어떤 강의를 어디까지 들었나)
            MemberHistoryDTO memberHistoryDTO = memberService.getMemberHistory(member.getId());
            model.addAttribute("courseHistory", memberHistoryDTO); // 수강관련
            model.addAttribute("courseQuestions", courseQuestions);
            model.addAttribute("course", courseDTO);
            model.addAttribute("courseClass", courseClassDTO);

            if(tab != null){
                model.addAttribute("tab", tab);
            }

            url = courseClassDTO.getName();
        }
        return "study/studyRoom/"+url;
    }

    /**
     * 수업을 다 듣고, 다음 강의를 눌렀을 때
     * @param id 강의 pk
     * @param class_id 수업 pk
     * @param member 유저
     * @param model
     * @return
     */
    @GetMapping(value = "/{id}/lecture/{class_id}")
    @PreAuthorize("isAuthenticated()")
    public String courseView(@PathVariable(name = "id") Long id, @PathVariable(name = "class_id") Long class_id,
                             @RequestParam(value = "tab", required = false) String tab,
                             @AuthenticationPrincipal Member member, Model model){
        String url = "";
        /* 해당 유저가 수강 관계를 맺고있는지 검사 */
        if(studyService.checkSignUpCourseMember(id, member.getId())){
            Course savedCourse = courseService.getCourse(id);
            CourseClass savedCourseClass = courseService.getCourseClass(class_id);

            CourseDTO courseDTO = savedCourse.of();
            CourseClassDTO courseClassDTO = savedCourseClass.of();

            if(member != null) { /* OSIV 때문에 직접 조회해서 줘야함 */
                MemberDTO memberDTO = (memberService.getMember(member.getId())).of();
                model.addAttribute("member", memberDTO);
            }

            if(studyService.hasCourseHistory(member.getId(), id)){ // 만약 다음 강의에 수업 기록이 있을 경우 기록객체 추가
                // 해당 강의의 기록
                CourseHistoryDTO courseHistory = studyService.getCourseHistory(member.getId(), savedCourseClass.getId());
                if(courseHistory != null){ // 해당 강의가 처음 볼 상황도 있음
                    model.addAttribute("currentHistory", courseHistory);
                }
                // 모든 강의의 기록
                List<CourseHistoryDTO> courseHistories = studyService.getCourseHistories(member.getId(), savedCourse.getId());
                model.addAttribute("courseHistories", courseHistories);
            }

            /*  해당 수업의 질문 */
            List<CourseQuestion> courseQuestions = courseQuestionService.getQuestionList(class_id);

            MemberHistoryDTO memberHistoryDTO = memberService.getMemberHistory(member.getId());
            model.addAttribute("courseHistory", memberHistoryDTO); // 수강관련
            model.addAttribute("courseQuestions", courseQuestions);
            model.addAttribute("course", courseDTO);
            model.addAttribute("courseClass", courseClassDTO);

            if(tab != null){
                model.addAttribute("tab", tab);
            }

            url = courseClassDTO.getName();
        }
        return "study/studyRoom/"+url;
    }

    /**
     * ajax로 수업 정보를 20초마다 혹은 수업 종료 시 수업 기록을 저장하는 메서드
     * @param id 강의 pk
     * @param class_id 수업 pk
     * @param currentTime 수강한 시간
     * @param member 유저
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/{id}/save/{class_id}")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public String saveStudyData(@PathVariable Long id, @PathVariable Long class_id, @RequestParam Double currentTime,
                                @AuthenticationPrincipal Member member) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        studyService.saveStudyData(id, class_id, member.getId(), ((int)Math.round(currentTime)));
        Map<String, String> map = new HashMap<>();
        map.put("result", "success");

        return mapper.writeValueAsString(map);
    }

    /**
     * 수업을 다 들었을 때, 다음 수업의 정보를 찾는 메서드
     * @param id 강의 pk
     * @param class_id 수업 pk
     * @return 다음 수업 json
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/{id}/next/{class_id}")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public String nextCourseClass(@PathVariable Long id, @PathVariable Long class_id) throws JsonProcessingException {
        String resultValue = "";
        ObjectMapper mapper = new ObjectMapper();
        CourseClass courseClass = studyService.nextCourseClass(id, class_id);
        if(courseClass != null){    // 만약 다음 강의가 있을 때,
            CourseClassDTO courseClassDTO = courseClass.of();
            resultValue = mapper.writeValueAsString(courseClassDTO);
        }else{
            Map<String, String> map = new HashMap<>();
            map.put("result", "end");
            resultValue = mapper.writeValueAsString(map);
        }
        return resultValue;
    }

    /***
     * 사용자가 강의를 장바구니에 넣음
     * @param member 사용자
     * @param id 강의 pk
     * @return
     */
    @PostMapping(value = "/add/cart")
    @PreAuthorize("isAuthenticated()")
    public String addCart(@AuthenticationPrincipal Member member,
                          @RequestParam(value = "course_id") Long id,
                          @RequestParam(value = "courseClass_id") Long classId,
                          RedirectAttributes redirectAttributes){
        memberService.addCourseCart(member, id);
        redirectAttributes.addFlashAttribute("modal", "success");
        return "redirect:/study/"+id+"/preview/"+classId;
    }
}
