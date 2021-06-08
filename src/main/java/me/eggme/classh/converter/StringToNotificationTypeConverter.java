package me.eggme.classh.converter;

import me.eggme.classh.domain.dto.NotificationType;
import org.springframework.core.convert.converter.Converter;

public class StringToNotificationTypeConverter implements Converter<String, NotificationType> {

    @Override
    public NotificationType convert(String s) {
        NotificationType notificationType = NotificationType.getValue(s);
        return notificationType;
    }
}
