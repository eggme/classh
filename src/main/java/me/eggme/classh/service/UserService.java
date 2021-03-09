package me.eggme.classh.service;

import me.eggme.classh.entity.User;
import me.eggme.classh.exception.EmailExistedException;
import me.eggme.classh.exception.PasswordWrongException;
import me.eggme.classh.repository.UserRepository;
import me.eggme.classh.utils.NameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User login(User user){
       User loadUser = userRepository.findByEmail(user.getEmail())
               .orElseThrow(() -> new EmailExistedException(user.getEmail()));
       authenticate(user.getPassword(), loadUser.getPassword());
       return loadUser;
    }

    public User signUp(String email, String password){
        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(NameGenerator.getName(email))
                .build();
        return userRepository.save(user);
    }

    private void authenticate(String pw, String loadPw){
        if(!passwordEncoder.matches(pw, loadPw)) new PasswordWrongException();
    }

}
