package com.jing.community.repository;

import com.jing.community.cache.TagCache;
import com.jing.community.entity.Notification;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    @Query("select t from Notification t where t.reciever = ?1 ORDER BY t.reciever ASC")
    List<Notification> findByReciever(String accountId);

    List<Notification> findByRecieverAndStatus(String accountId, Integer i);
}
