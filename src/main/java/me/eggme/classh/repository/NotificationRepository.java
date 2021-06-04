package me.eggme.classh.repository;

import me.eggme.classh.domain.entity.Member;
import me.eggme.classh.domain.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findTop6ByMember(Member member);
}
