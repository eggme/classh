package me.eggme.classh.service;

import me.eggme.classh.entity.Member;
import me.eggme.classh.exception.EmailExistedException;
import me.eggme.classh.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MemberBoardService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MemberRepository memberRepository;

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
}
