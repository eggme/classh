package me.eggme.classh.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationType {
    NOTICE("[공지사항]"),
    NEW_COURSE("[신규강의]"),
    COURSE_NOTICE("[강의새소식]"),
    INSTRUCTOR_NOTICE("[강사알림]"),
    QUESTION_ANSWER("[질문답변]");

    private String value;
}
