package me.eggme.classh.service;

import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.entity.Course;
import me.eggme.classh.domain.entity.Member;
import me.eggme.classh.domain.entity.SignUpCourse;
import me.eggme.classh.exception.EmailExistedException;
import me.eggme.classh.exception.NoSearchCourseException;
import me.eggme.classh.repository.CourseRepository;
import me.eggme.classh.repository.MemberRepository;
import me.eggme.classh.utils.FileUploadFactory;
import me.eggme.classh.utils.FileUploader;
import me.eggme.classh.utils.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MemberBoardService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private MemberRepository memberRepository;

    private FileUploader fileUploader;

    @Transactional
    public void changePassword(String current_pw, String new_pw, String username){
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new EmailExistedException(username));
        String password = member.getPassword();
        if(validatePassword(current_pw, password)){
            member.setPassword(passwordEncoder.encode(new_pw));
        }
    }

    private boolean validatePassword(String input_pw, String password){
        if(passwordEncoder.matches(input_pw, password))
            return true;
        return false;
    }

    @Transactional
    public Member changeName(String name, String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new EmailExistedException(username));
        member.setNickName(name);
        /*
            시큐리티 세션 업데이트
         */
        Authentication beforeAuthentication = SecurityContextHolder.getContext().getAuthentication();
        Member authenticationObject = ((Member)beforeAuthentication.getPrincipal());
        authenticationObject.setNickName(member.getNickName());
        Authentication afterAuthentication = new UsernamePasswordAuthenticationToken(authenticationObject, null, beforeAuthentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(afterAuthentication);

        return member;
    }

    /***
     * 프로필 설정에서 사용자가 이미지를 변경할 때 호출되는 메소드
     * 파일정보를 받아서 이미지로 변경한 후 Member 엔티티에 profile 속성에 저장
     * @param file 파일 정보
     * @param username 해당되는 유저의 이름
     * @throws Exception
     */
    @Transactional
    public void changeProfile(File file, String username) throws Exception{
        fileUploader = FileUploadFactory.getFileUploader(ResourceType.IMAGE);
        String profileURL = fileUploader.saveFile(file, ResourceType.IMAGE);
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new EmailExistedException(username));
        member.setProfile(profileURL);
        /*
            시큐리티 세션 업데이트
         */
        Authentication beforeAuthentication = SecurityContextHolder.getContext().getAuthentication();
        Member authenticationObject = ((Member)beforeAuthentication.getPrincipal());
        authenticationObject.setProfile(member.getProfile());
        Authentication afterAuthentication = new UsernamePasswordAuthenticationToken(authenticationObject, null, beforeAuthentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(afterAuthentication);
    }

    public Member loadMember(String username) {
        return memberRepository.findByUsername(username).orElseThrow(() -> new EmailExistedException(username));
    }

    @Transactional
    public void changeSelfIntroduce(String self, String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new EmailExistedException(username));
        member.setSelfIntroduce(self);
    }

    @Transactional
    public Set<Course> getCourseSet(Member member) {
        Member savedMember = memberRepository.findById(member.getId()).orElseThrow(() ->
                new UsernameNotFoundException("해당되는 유저를 찾을 수 없습니다"));
        log.info("MemberBoardService");
        Set<SignUpCourse> signUpCourses = savedMember.getSignUpCourses();
        Set<Course> courseSet = signUpCourses.stream().map(suc ->
             courseRepository.findById(suc.getCourse().getId()).orElseThrow(() ->
                    new NoSearchCourseException())
        ).collect(Collectors.toSet());
        log.info(courseSet.stream().map(c-> c.getName()).collect(Collectors.joining(", ")));
        return courseSet;
    }
}
