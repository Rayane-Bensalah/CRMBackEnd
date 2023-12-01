package com.example.CrmBackend;

import com.example.CrmBackend.model.User;
import com.example.CrmBackend.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;

    @Test
    void UserRepository_SaveUser() {

        // Create test user and save it
        User testUser = new User("testUsername", "testFirstname", "testLastname", "test@email.com");
        userRepository.save(testUser);

        // Assert user is created and with correct id
        Assertions.assertThat(testUser).isNotNull();
        Assertions.assertThat(testUser.getId()).isGreaterThan(0);
    }

    @Test
    public void UserRepository_GetAllUsers() {
        // Create and save 2 users
        User testUser1 = new User("testUsername1", "testFirstname1", "testLastname1", "test@email1.com");
        User testUser2 = new User("testUsername2", "testFirstname2", "testLastname2", "test@email2.com");

        userRepository.save(testUser1);
        userRepository.save(testUser2);

        // Get all users
        List<User> userList = userRepository.findAll();

        // Assert both users are created and saved
        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList.size()).isEqualTo(2);
    }

}