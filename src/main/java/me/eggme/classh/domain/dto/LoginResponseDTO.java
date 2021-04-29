package me.eggme.classh.domain.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO implements Serializable {
    private String code;
    private String status;
    private String message;
    private Object item;
}
