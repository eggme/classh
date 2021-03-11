package me.eggme.classh.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import me.eggme.classh.dto.LoginResponseDTO;
import me.eggme.classh.dto.ResponseDataCode;
import me.eggme.classh.dto.ResponseStatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ObjectMapper mapper = new ObjectMapper();
        LoginResponseDTO dto = new LoginResponseDTO();
        dto.setCode(ResponseDataCode.SUCCESS);
        dto.setStatus(ResponseStatusCode.SUCCESS);
        Map<String, String> items = new HashMap<>();
        items.put("url", "/");
        dto.setItem(items);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("UTF-8");
        response.getWriter().println(mapper.writeValueAsString(dto));
        log.info(mapper.writeValueAsString(dto));
        response.flushBuffer();
    }
}
