package me.eggme.classh.config;

import me.eggme.classh.converter.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestAttributeMethodArgumentResolver;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Bean
    public RequestAttributeMethodArgumentResolver requestAttributeMethodArgumentResolver(){
        return new RequestAttributeMethodArgumentResolver();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToCourseCategoryConverter());
        registry.addConverter(new StringToCourseLevelConverter());
        registry.addConverter(new StringToCourseStatusConverter());
        registry.addFormatter(new RecommendationFormatter());
        registry.addFormatter(new SkillTagFormatter());
    }
}
