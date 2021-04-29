package me.eggme.classh.security.handler;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.exception.EmailExistedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.security.auth.login.CredentialExpiredException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final String USERNAME = "USERNAME";
    private final String PASSWORD = "PASSWORD";
    private final String ERROR_MESSAGE = "ERROR_MSG";
    private final String DEFAULT_FAILURE_URL = "/login/error";

    private Map<Class, String> errorMsgList;

    public CustomAuthenticationFailureHandler(){
        errorMsgList = new HashMap<>();
        errorMsgList.put(EmailExistedException.class, "error.BadCredentials");
        errorMsgList.put(BadCredentialsException.class, "error.BadCredentials");
        errorMsgList.put(InternalAuthenticationServiceException.class, "error.BadCredentials");
        errorMsgList.put(DisabledException.class, "error.Disaled");
        errorMsgList.put(CredentialExpiredException.class, "error.CredentialsExpired");
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        HttpSession session = httpServletRequest.getSession();
        session.removeAttribute("username");

        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");

        httpServletRequest.setAttribute(USERNAME, username);
        httpServletRequest.setAttribute(PASSWORD, password);
        httpServletRequest.setAttribute(ERROR_MESSAGE, errorMsgList.get(e.getClass()));

        httpServletRequest.getRequestDispatcher(DEFAULT_FAILURE_URL).forward(httpServletRequest, httpServletResponse);
    }
}
