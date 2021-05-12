package me.eggme.classh;

import me.eggme.classh.domain.dto.QuestionStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FormatterTest {

    @Test
    public void ConvertingTest(){
        QuestionStatus questionStatus = QuestionStatus.findByValue("미해결");
        System.out.println(questionStatus.getValue());
    }

}
