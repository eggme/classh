package me.eggme.classh.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  QuestionStatus {
    RESOLVED("해결"),
    UNRESOLVED("미해결");

    private String value;
}
