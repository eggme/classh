package me.eggme.classh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.CourseNoteDTO;
import me.eggme.classh.domain.entity.CourseNote;
import me.eggme.classh.domain.entity.Member;
import me.eggme.classh.service.CourseNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequestMapping(value = "/note")
@PreAuthorize("isAuthenticated()")
@Slf4j
public class CourseNoteController {

    @Autowired
    private CourseNoteService courseNoteService;

    /**
     * 수업 페이지에서 노트 입력 시 ajax로 요청된 데이터를 엔티티로 저장
     * @param member 노트를 작성한 유저
     * @param courseNote 노트 데이터
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/add/json")
    @ResponseBody
    public String addNote(@AuthenticationPrincipal Member member,
                          @RequestParam(value = "course") Long course_id,
                          @RequestParam(value = "courseClass") Long class_id,
                          @ModelAttribute CourseNote courseNote) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        CourseNoteDTO courseNoteDTO = courseNoteService.saveCourseNote(member, course_id, class_id, courseNote);

        return mapper.writeValueAsString(courseNoteDTO);
    }

    /**
     * 수업 페이지에서 노트 수정 시 ajax로 수정될 노트 데이터를 받아서 엔티티에 저장
     * @param note_id 노트 pk
     * @param courseNote 수정될 노트 데이터
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/edit/json")
    @ResponseBody
    public String editNote(@RequestParam(value = "note_id") Long note_id,
                           @ModelAttribute CourseNote courseNote) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CourseNoteDTO courseNoteDTO = courseNoteService.editCourseNote(note_id, courseNote);
        log.info(courseNoteDTO.toString());

        return mapper.writeValueAsString(courseNoteDTO);
    }


    @PostMapping(value = "/delete/json")
    @ResponseBody
    public String deleteNote(@RequestParam(value = "note_id") Long note_id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> map = new HashMap<>();

        courseNoteService.deleteCourseNote(note_id);

        map.put("result", "success");
        return mapper.writeValueAsString(map);
    }
}
