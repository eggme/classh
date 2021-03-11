package me.eggme.classh.utils;

import org.springframework.web.bind.annotation.ValueConstants;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestAttribute {
    String value() default "";
    boolean required() default true;
    String defaultValue() default ValueConstants.DEFAULT_NONE;
}
