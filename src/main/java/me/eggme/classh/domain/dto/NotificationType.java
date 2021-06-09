package me.eggme.classh.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum NotificationType {
    NOTICE("[시스템 공지사항]" , "/community/notification"),
    NEW_COURSE("[신규강의]" , "/notice"),
    COURSE_NOTICE("[강의새소식]", "/course"),
    INSTRUCTOR_NOTICE("[강사알림]", "/course"),
    QUESTION_ANSWER("[질문답변]", "/question"),
    MD_NOTICE("[공지사항]", "/community/notification");

    private String value;
    private String url;

    public static NotificationType getValue(String value){
        NotificationType findNotificationType = Arrays.stream(NotificationType.values()).filter(notificationType ->
                notificationType.getValue().equals(value)).findFirst().get();
        return findNotificationType;
    }
}
