package me.eggme.classh.handler;

import lombok.extern.slf4j.Slf4j;
import me.eggme.classh.domain.dto.NotificationEvent;
import me.eggme.classh.domain.dto.NotificationType;
import me.eggme.classh.domain.entity.Member;
import me.eggme.classh.domain.entity.Notification;
import me.eggme.classh.repository.MemberRepository;
import me.eggme.classh.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Slf4j
public class NotificationHandler {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private MemberRepository memberRepository;

    @EventListener
    @Transactional
    @Async
    public void publishedNotification(NotificationEvent event) {
        Member savedMember = memberRepository.findById(event.getWriter().getId()).orElseThrow(() ->
                new UsernameNotFoundException("해당되는 유저를 찾을 수 없습니다."));

        event.getMemberList().stream().forEach(m -> {
            Notification notification = Notification.builder()
                    .title(event.getTitle())
                    .content(event.getContent())
                    .notificationType(event.getNotificationType())
                    .writer(event.getWriter())
                    .build();
            Notification savedNotification = notificationRepository.save(notification);
            savedNotification.setMember(m);
            savedMember.addNotification(savedNotification);
            log.info(event.getWriter().getNickName() + "님이 " + m.getNickName() + "님에게 "+ event.getContent() +" 메시지를 전송하였습니다");
        });
    }
}
