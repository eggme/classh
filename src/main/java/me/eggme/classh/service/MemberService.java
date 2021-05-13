package me.eggme.classh.service;

import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.MemberDTO;
import me.eggme.classh.domain.entity.Member;
import me.eggme.classh.domain.entity.MemberRoles;
import me.eggme.classh.repository.MemberRepository;
import me.eggme.classh.repository.MemberRolesRepository;
import me.eggme.classh.repository.RoleRepository;
import me.eggme.classh.domain.entity.Role;
import me.eggme.classh.utils.NameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberRolesRepository memberRolesRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public Long save(MemberDTO memberDTO) {

        Member member = Member.builder()
                .username(memberDTO.getUsername())
                .password(passwordEncoder.encode(memberDTO.getPassword()))
                .nickName(NameGenerator.getName(memberDTO.getUsername()))
                .build();
        Member savedMember = memberRepository.save(member);
        defaultMemberRoleCreator(savedMember);
        return memberRepository.save(member).getId();
    }

    @Transactional
    protected void defaultMemberRoleCreator(Member member){
        Role role = roleRepository.defaultUserRole();
        MemberRoles memberRoles = new MemberRoles();
        memberRoles.setMember(member);
        memberRoles.setRole(role);
        memberRolesRepository.save(memberRoles);
    }

    @Transactional
    public Member loadUser(String username) {
        return memberRepository.findByUsername(username).orElseThrow(() -> new ArithmeticException());
    }

    @Transactional
    public String loadUserNickname(String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new ArithmeticException());
        return member.getNickName();
    }
}
