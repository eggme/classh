package me.eggme.classh.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String email;
    private String email_confirm;
    private String password;
    private String password_confirm;
}
