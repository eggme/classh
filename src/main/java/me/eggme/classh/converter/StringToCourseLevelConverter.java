package me.eggme.classh.converter;

import me.eggme.classh.dto.CourseLevel;
import org.springframework.core.convert.converter.Converter;

public class StringToCourseLevelConverter implements Converter<String, CourseLevel> {

    @Override
    public CourseLevel convert(String s) {
        return CourseLevel.findLevel(s);
    }
}
