package me.eggme.classh.converter;

import me.eggme.classh.domain.dto.CourseState;
import org.springframework.core.convert.converter.Converter;

public class StringToCourseStatusConverter implements Converter<String, CourseState> {

    @Override
    public CourseState convert(String s) {
        CourseState courseState = CourseState.getState(s);
        return courseState;
    }
}
