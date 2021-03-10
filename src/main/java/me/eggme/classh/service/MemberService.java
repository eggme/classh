package me.eggme.classh.service;

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
        //return memberRepository.findByEmail(email).map(m -> new UserDetailsVO(m, Collections.singleton(
        //        new SimpleGrantedAuthority(m.getUserRole().getValue())))).orElseThrow(() -> new EmailExistedException(email));
    }

    @Autowired
    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder){
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member login(String username, String password){
        System.out.println(username + " : " + password);
        Member loadMember = memberRepository.findByEmail(username)
               .orElseThrow(() -> new EmailExistedException(username));
        authenticate(password, loadMember.getPassword());
        return loadMember;
    }


    private void authenticate(String pw, String loadPw){
        if(!passwordEncoder.matches(pw, loadPw)) new PasswordWrongException();
    }

}
