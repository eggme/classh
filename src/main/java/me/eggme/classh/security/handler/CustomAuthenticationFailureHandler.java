package me.eggme.classh.security.handler;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.exception.EmailExistedException;
import me.eggme.classh.utils.ExceptionMessages;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.security.auth.login.CredentialExpiredException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = ExceptionMessages.getErrorMsgList().get(BadCredentialsException.class);
        try{
            errorMessage = ExceptionMessages.getErrorMsgList().get(exception.getClass());
        }catch(Exception e){
            errorMessage = ExceptionMessages.getErrorMsgList().get(BadCredentialsException.class);
        }
        setDefaultFailureUrl("/login?error=true&exception="+ errorMessage);

        super.onAuthenticationFailure(request, response, exception);
    }
}
