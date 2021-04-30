package me.eggme.classh.security.service;

import me.eggme.classh.domain.entity.Member;
import me.eggme.classh.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username).orElse(null);
        if(member == null){
            if(memberRepository.countByUsername(username) == 0){
                throw new UsernameNotFoundException("No user found with username :" +username);
            }
        }
        List<SimpleGrantedAuthority> collect = member.getMemberRoles()
                .stream().map(memberRoles -> memberRoles.getRole())
                .collect(Collectors.toList())
                .stream().map(r -> r.getRoleName()).collect(Collectors.toList())
                .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

//        CustomUserDetailsVO customUserDetailsVO = CustomUserDetailsVO.builder()
//                .username(member.getUsername())
//                .password(member.getPassword())
//                .profile(member.getProfile())
//                .nickname(member.getNickName())
//                .authorityList(collect)
//                .build();

        return new AccountContext(member, collect);
    }
}
