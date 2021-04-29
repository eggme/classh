package me.eggme.classh.service;

import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.entity.Member;
import me.eggme.classh.exception.EmailExistedException;
import me.eggme.classh.repository.MemberRepository;
import me.eggme.classh.repository.RoleRepository;
import me.eggme.classh.security.CustomUserDetailsService;
import me.eggme.classh.domain.entity.Role;
import me.eggme.classh.utils.NameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
@Slf4j
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public Long save(Member member) {
        member.setName(NameGenerator.getName(member.getEmail()));
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        Role role = new Role();
        role.setRoleName("ROLE_USER");
        role.setRoleDesc("일반유저");
        roleRepository.save(role);
        return memberRepository.save(member).getId();
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
