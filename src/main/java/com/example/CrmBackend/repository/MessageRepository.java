package com.example.CrmBackend.repository;

import com.example.CrmBackend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * repository for entity Message extending JpaRepository
 */
public interface MessageRepository extends JpaRepository<Message, Integer> {
}