package me.eggme.classh.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.LoginResponseDTO;
import me.eggme.classh.domain.dto.ResponseDataCode;
import me.eggme.classh.domain.dto.ResponseStatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info(authentication.getName());
        HttpSession session = request.getSession();
        session.setAttribute("username", authentication.getName());

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
