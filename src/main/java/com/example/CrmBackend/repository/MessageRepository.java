package com.example.CrmBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CrmBackend.model.Message;

/**
 * repository for entity Message extending JpaRepository
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
}