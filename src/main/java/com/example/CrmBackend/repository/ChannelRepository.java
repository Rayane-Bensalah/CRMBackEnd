package com.example.CrmBackend.repository;

import com.example.CrmBackend.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * repository for entity Channel extending JpaRepository
 */
public interface ChannelRepository extends JpaRepository<Channel, Long> {
}
