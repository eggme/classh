package me.eggme.classh.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestAttributeMethodArgumentResolver;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {"classpath:/static/", "classpath:/public/", "classpath:/resources",
        "classpath:/META-INF/resources/" ,"claspath:/META-INF/resources/webjars", "classpath:/resources/imgs/", "classpath:/resources/css/",
        "classpath:/resources/js/", "classpath:/resources/video/"};

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("root/index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Bean
    public RequestAttributeMethodArgumentResolver requestAttributeMethodArgumentResolver(){
        return new RequestAttributeMethodArgumentResolver();
    }
}
