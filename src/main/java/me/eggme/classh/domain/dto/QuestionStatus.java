package me.eggme.classh.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum  QuestionStatus {
    RESOLVED("해결"),
    UNRESOLVED("미해결");

    private String value;

    public static QuestionStatus findByValue(String value){
        QuestionStatus questionStatus = Arrays.stream(QuestionStatus.values()).filter(q ->
                !q.getValue().equals(value)).findFirst().get();
        return questionStatus;
    }
}
