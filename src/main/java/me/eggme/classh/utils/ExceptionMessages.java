package me.eggme.classh.utils;

import me.eggme.classh.exception.EmailExistedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.security.auth.login.CredentialExpiredException;
import java.util.HashMap;
import java.util.Map;

public class ExceptionMessages {

    private static Map<Class, String> errorMsgList;

    static{
        errorMsgList = new HashMap<>();
        errorMsgList.put(EmailExistedException.class, "error.BadCredentials");
        errorMsgList.put(BadCredentialsException.class, "error.BadCredentials");
        errorMsgList.put(InternalAuthenticationServiceException.class, "error.BadCredentials");
        errorMsgList.put(DisabledException.class, "error.Disabled");
        errorMsgList.put(CredentialExpiredException.class, "error.CredentialsExpired");
        errorMsgList.put(UsernameNotFoundException.class, "error.UserNotFound");
    }

    public static Map<Class, String> getErrorMsgList(){
        return errorMsgList;
    }

    public static void addErrorMsg(Class exceptionClass, String messageFormat){
        if( errorMsgList.get(exceptionClass) == null){
            errorMsgList.put(exceptionClass, messageFormat);
        }
    }
}
