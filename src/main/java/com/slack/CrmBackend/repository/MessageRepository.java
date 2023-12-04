package com.slack.CrmBackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.slack.CrmBackend.model.Channel;
import com.slack.CrmBackend.model.Message;
import com.slack.CrmBackend.model.User;

/**
 * repository for entity Message extending JpaRepository
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByUser(User user);

    List<Message> findByChannel(Channel channel);

    List<Message> findByUserAndChannel(User user, Channel channel);
}