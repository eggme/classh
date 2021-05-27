package me.eggme.classh.interceptor;

import me.eggme.classh.domain.dto.CourseCategory;
import me.eggme.classh.domain.dto.CourseLevel;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.EnumSet;

public class CategoriesAndLevelInterceptor implements HandlerInterceptor {

    private static EnumSet categories = EnumSet.allOf(CourseCategory.class);
    private static EnumSet levels = EnumSet.allOf(CourseLevel.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        request.setAttribute("categories", categories);
        request.setAttribute("levels", levels);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
