package me.eggme.classh.security.handler;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.utils.ExceptionMessages;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Setter
    private String errorPage;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        log.info(e.getMessage());
        e.printStackTrace();
        String exception = ExceptionMessages.getErrorMsgList().get(e);
        String deniedUrl = errorPage + "?exception=" + exception;
        httpServletResponse.sendRedirect(deniedUrl);
    }
}
