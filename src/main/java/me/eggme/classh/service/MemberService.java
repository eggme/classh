package me.eggme.classh.service;

import lombok.extern.log4j.Log4j2;
import me.eggme.classh.entity.Member;
import me.eggme.classh.exception.EmailExistedException;
import me.eggme.classh.exception.PasswordWrongException;
import me.eggme.classh.repository.MemberRepository;
import me.eggme.classh.security.UserDetailsVO;
import me.eggme.classh.security.UserRole;
import me.eggme.classh.utils.NameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
@Log4j2
public class MemberService implements MemberSecurityService {

    private MemberRepository memberRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Long save(Member member) {
        member.setName(NameGenerator.getName(member.getEmail()));
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setUserRole(UserRole.USER);
        return memberRepository.save(member).getId();
    }

    @Override
    public UserDetailsVO loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EmailExistedException(email));
        UserDetailsVO userDetailsVO = new UserDetailsVO(member, Collections.singleton(new SimpleGrantedAuthority(member.getUserRole().getValue())));
        return userDetailsVO;
    }

    @Autowired
    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder){
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member loadUser(String username) {
        return memberRepository.findByEmail(username).orElseThrow(() -> new ArithmeticException());
    }

    public String loadUserName(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new ArithmeticException());
        log.info(member.getEmail() + " : " + member.getName());
        return member.getName();
    }
}
