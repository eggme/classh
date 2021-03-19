package me.eggme.classh.converter;

import me.eggme.classh.dto.CourseCategory;
import org.springframework.core.convert.converter.Converter;

public class StringToCourseCategoryConverter implements Converter<String, CourseCategory> {

    @Override
    public CourseCategory convert(String s) {
        return CourseCategory.findCategory(s);
    }
}
