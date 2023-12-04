package com.slack.CrmBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.slack.CrmBackend.model.Channel;

/**
 * repository for entity Channel extending JpaRepository
 */
@Repository
public interface ChannelRepository extends JpaRepository<Channel, Integer> {
}
