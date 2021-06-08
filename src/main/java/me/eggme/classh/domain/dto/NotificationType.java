package me.eggme.classh.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum NotificationType {
    NOTICE("[공지사항]"),
    NEW_COURSE("[신규강의]"),
    COURSE_NOTICE("[강의새소식]"),
    INSTRUCTOR_NOTICE("[강사알림]"),
    QUESTION_ANSWER("[질문답변]");

    private String value;

    public static NotificationType getValue(String value){
        NotificationType findNotificationType = Arrays.stream(NotificationType.values()).filter(notificationType ->
                notificationType.getValue().equals(value)).findFirst().get();
        return findNotificationType;
    }
}
