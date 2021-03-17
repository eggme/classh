package me.eggme.classh.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class MemberDTO implements Serializable {

    private String name;
    private String email;
    private String profile;

}
