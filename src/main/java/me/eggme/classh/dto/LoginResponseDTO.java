package me.eggme.classh.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginResponseDTO {
    private String code;
    private String status;
    private String message;
    private Object item;
}
