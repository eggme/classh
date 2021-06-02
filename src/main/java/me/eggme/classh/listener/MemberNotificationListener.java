package me.eggme.classh.listener;

import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.entity.Member;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import java.util.stream.Collectors;

@Slf4j
public class MemberNotificationListener {

    @PostUpdate
    public void postUpdate(Member member){
        String data = member.getNotifications().stream().map(n ->
                n.getContent()).collect(Collectors.joining(", "));
        log.info(member.getNickName() + "의 알림 " + data);
    }

}
