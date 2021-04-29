package me.eggme.classh.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.eggme.classh.domain.entity.Member;
import me.eggme.classh.repository.MemberRepository;
import me.eggme.classh.security.service.AccountContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElse(null);
        if(member == null){
            if(memberRepository.countByEmail(email) == 0){
                throw new UsernameNotFoundException("No user found with username :" +email);
            }
        }
        List<GrantedAuthority> collect = member.getMemberRoles().getRoles()
                .stream().map(role -> role.getRoleName())
                .collect(Collectors.toSet())
                .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        return new AccountContext(member, collect);
    }
}
