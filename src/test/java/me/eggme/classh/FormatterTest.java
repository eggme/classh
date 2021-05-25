package me.eggme.classh;

import me.eggme.classh.domain.dto.CourseCommentDTO;
import me.eggme.classh.domain.dto.CourseState;
import me.eggme.classh.domain.dto.QuestionStatus;
import me.eggme.classh.domain.entity.CourseComment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FormatterTest {

    @Test
    public void ConvertingTest(){
        QuestionStatus questionStatus = QuestionStatus.findByValue("미해결");
        System.out.println(questionStatus.getValue());

        Set<CourseComment> list = new LinkedHashSet<>();
        CourseComment courseCommentDTO = CourseComment.builder().commentContent("1").build();
        courseCommentDTO.setChildren(new LinkedHashSet<>());
        CourseComment courseCommentDTO_child1 = CourseComment.builder().commentContent("2").build();
        CourseComment courseCommentDTO_child2 = CourseComment.builder().commentContent("3").build();
        CourseComment courseCommentDTO_child3 = CourseComment.builder().commentContent("4").build();
        courseCommentDTO.getChildren().add(courseCommentDTO_child1);
        courseCommentDTO.getChildren().add(courseCommentDTO_child2);
        courseCommentDTO.getChildren().add(courseCommentDTO_child3);
        list.add(courseCommentDTO);
        System.out.println(list.size());
    }

    @Test
    public void ConvertCourseState(){
        CourseState courseState = CourseState.getState("승인완료");
        System.out.println(courseState.toString());
    }

}
