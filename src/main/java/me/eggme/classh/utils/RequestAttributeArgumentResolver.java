package me.eggme.classh.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;


public class RequestAttributeArgumentResolver implements HandlerMethodArgumentResolver {

    @SuppressWarnings("unchecked")
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        Annotation[] parameterAnnotations = methodParameter.getParameterAnnotations();
        Class<?> parameterType = methodParameter.getParameterType();

        for(Annotation annotation : parameterAnnotations){
            if(RequestAttribute.class.isInstance(annotation)){
                RequestAttribute requestAttribute = (RequestAttribute) annotation;
                HttpServletRequest httpServletRequest = (HttpServletRequest) nativeWebRequest.getNativeRequest();

                Object result = httpServletRequest.getAttribute(requestAttribute.value());
                return result;
            }
        }
        return WebArgumentResolver.UNRESOLVED;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(RequestAttribute.class);
    }

}
