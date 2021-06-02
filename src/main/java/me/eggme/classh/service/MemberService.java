package me.eggme.classh.service;

import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.MemberDTO;
import me.eggme.classh.domain.dto.MemberHistoryDTO;
import me.eggme.classh.domain.entity.*;
import me.eggme.classh.exception.NoSearchCourseClassException;
import me.eggme.classh.exception.NoSearchCourseException;
import me.eggme.classh.repository.*;
import me.eggme.classh.utils.NameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

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
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CourseRepository courseRepository;

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

    /***
     * 유저를 지식공유자로 수정
     * @param member 유저
     * @param additionalMemberData 지식공유자 추가입력 정보
     */
    @Transactional
    public void createInstructor(Member member, Member additionalMemberData) {
        Member savedMember = memberRepository.findById(member.getId()).orElseThrow(() ->
                new UsernameNotFoundException("해당되는 유저가 없습니다"));
        savedMember.setEmail(additionalMemberData.getEmail());
        savedMember.setMemberName(additionalMemberData.getMemberName());
        savedMember.setTel(additionalMemberData.getTel());

        if(savedMember.getInstructor() == null) {
            Instructor instructor = new Instructor();
            Instructor savedInstructor = instructorRepository.save(instructor);

            savedInstructor.setMember(savedMember);
            savedMember.setInstructor(savedInstructor);
            /*
                시큐리티 세션 업데이트
            */
            Authentication beforeAuthentication = SecurityContextHolder.getContext().getAuthentication();
            Member authenticationObject = ((Member)beforeAuthentication.getPrincipal());
            authenticationObject.setProfile(member.getProfile());
            authenticationObject.setInstructor(savedInstructor);
            Authentication afterAuthentication = new UsernamePasswordAuthenticationToken(authenticationObject, null, beforeAuthentication.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(afterAuthentication);
        }
    }

    /***
     * 해당 유저가 강의를 장바구니에 담음
     * @param member 유저 정보
     * @param id 강의 pk
     */
    @Transactional
    public void addCourseCart(Member member, Long id) {
        Member savedMember = memberRepository.findById(member.getId()).orElseThrow(() ->
                new UsernameNotFoundException("해당 유저를 찾을 수 없습니다"));
        Course savedCourse = courseRepository.findById(id).orElseThrow(() -> new NoSearchCourseException());
        Cart cart = null;
        if(savedMember.getCart() == null) { // 기존 장바구니 객체가 없을 때
            Cart tempCart = new Cart();
            cart = cartRepository.save(tempCart);
            cart.setMember(savedMember);
            cart.addCourse(savedCourse);
            savedMember.setCart(cart);
        }else{
            cart = cartRepository.findById(savedMember.getCart().getId()).orElse(null);
            if(cart != null){
                cart.addCourse(savedCourse);
                savedMember.setCart(null);
                savedMember.setCart(cart);
            }
        }
        if( cart != null){
            Authentication beforeAuthentication = SecurityContextHolder.getContext().getAuthentication();
            Member authenticationObject = ((Member)beforeAuthentication.getPrincipal());
            authenticationObject.setCart(cart);
            Authentication afterAuthentication = new UsernamePasswordAuthenticationToken(authenticationObject, null, beforeAuthentication.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(afterAuthentication);
        }
    }

    @Transactional
    public Set<Course> selectCart(Member member){
        Member savedMember = memberRepository.findById(member.getId()).orElseThrow(() ->
                new UsernameNotFoundException("해당 유저를 찾을 수 없습니다"));
        if(savedMember.getCart() != null) {
            Cart savedCart = cartRepository.findById(savedMember.getCart().getId()).orElse(null);
            if (savedCart != null) return savedCart.getCourses();
        }
        return null;
    }

    @Transactional
    public MemberHistoryDTO getMemberHistory(Long memberId) {
        Member savedMember = memberRepository.findById(memberId).orElseThrow(() ->
                new UsernameNotFoundException("해당되는 유저가 존재하지 않습니다"));
        return savedMember.ofHistory();
    }
}
