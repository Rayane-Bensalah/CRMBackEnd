package com.example.CrmBackend.repository;

import com.example.CrmBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * repository for entity User extending JpaRepository
 */
public interface UserRepository extends JpaRepository<User, Long> {
}