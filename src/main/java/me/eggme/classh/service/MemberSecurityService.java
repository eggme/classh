package me.eggme.classh.service;

import me.eggme.classh.security.UserDetailsVO;
import me.eggme.classh.entity.Member;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface MemberSecurityService extends UserDetailsService {

    Long save(Member member);

    @Override
    UserDetailsVO loadUserByUsername(String s) throws UsernameNotFoundException;
}
