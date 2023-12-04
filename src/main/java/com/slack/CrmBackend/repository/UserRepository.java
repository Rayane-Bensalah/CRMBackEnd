package com.slack.CrmBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.slack.CrmBackend.model.User;

/**
 * repository for entity User extending JpaRepository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}