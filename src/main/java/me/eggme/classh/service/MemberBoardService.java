package me.eggme.classh.service;

import me.eggme.classh.entity.Member;
import me.eggme.classh.exception.EmailExistedException;
import me.eggme.classh.repository.MemberRepository;
import me.eggme.classh.utils.FileUploadFactory;
import me.eggme.classh.utils.FileUploader;
import me.eggme.classh.utils.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;

@Service
public class MemberBoardService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    private FileUploader fileUploader;

    @Transactional
    public void changePassword(String current_pw, String new_pw, String email){
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EmailExistedException(email));
        String password = member.getPassword();
        if(validatePassword(current_pw, password)){
            member.setPassword(bCryptPasswordEncoder.encode(new_pw));
        }
    }

    private boolean validatePassword(String input_pw, String password){
        if(bCryptPasswordEncoder.matches(input_pw, password))
            return true;
        return false;
    }

    @Transactional
    public void changeName(String name, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EmailExistedException(email));
        member.setName(name);
    }

    @Transactional
    public void changeProfile(File file, String email) throws Exception{
        fileUploader = FileUploadFactory.getFileUploader(ResourceType.IMAGE);
        String profileURL = fileUploader.saveFile(file, ResourceType.IMAGE);
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EmailExistedException(email));
        member.setProfile(profileURL);
    }

    public Member loadMember(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new EmailExistedException(email));
    }

    @Transactional
    public void changeSelfIntroduce(String self, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EmailExistedException(email));
        member.setSelfIntroduce(self);
    }
}
