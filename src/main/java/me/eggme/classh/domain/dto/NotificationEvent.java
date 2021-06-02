package me.eggme.classh.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.eggme.classh.domain.entity.Member;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class NotificationEvent {

    private List<Member> memberList = new ArrayList<>();
    private Member writter;
    private String content;

}
