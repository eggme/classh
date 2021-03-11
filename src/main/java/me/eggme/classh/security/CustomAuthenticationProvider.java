package me.eggme.classh.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.eggme.classh.exception.EmailExistedException;
import me.eggme.classh.exception.PasswordWrongException;
import me.eggme.classh.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Log4j2
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MemberService memberService;

    @NonNull
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String userEmail = token.getName();
        String userPw = (String) token.getCredentials();
        // UserDetailService를 통해 DB에서 아이디로 사용자 조회
        UserDetailsVO userDetailsVO = (UserDetailsVO) memberService.loadUserByUsername(userEmail);
        if(userDetailsVO == null){
            throw new EmailExistedException(userEmail);
        }else if(!passwordEncoder.matches(userPw, userDetailsVO.getPassword()))
            throw new PasswordWrongException();

        return new UsernamePasswordAuthenticationToken(userDetailsVO, userPw, userDetailsVO.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
