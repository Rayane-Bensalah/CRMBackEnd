package com.slack.CrmBackend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.slack.CrmBackend.model.User;

/**
 * Tests for UserRepository
 * tested save() and findAll()
 */
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2) // Simulate a Database
@DataJpaTest
class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;

    /**
     * test save()
     */
    @Test
    void UserRepository_SaveUser() {

        // Create test user and save it
        User testUser = new User("testUsername", "testFirstname", "testLastname", "test@email.com");
        userRepository.save(testUser);

        // Assert user is created and with correct id
        Assertions.assertThat(testUser).isNotNull();
        Assertions.assertThat(testUser.getId()).isGreaterThan(0);
    }

    /**
     * test findAll()
     */
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

    /**
     * test update
     */
    @Test
    void UserRepository_UpdateUser() {
        // Create and save a test User
        User testUser = new User("testUsername", "testFirstname", "testLastname", "test@email.com");
        userRepository.save(testUser);

        // Modify the User's properties
        testUser.setUserName("updatedUserName");
        testUser.setFirstName("updatedFirstName");
        testUser.setLastName("updatedLastName");
        testUser.setEmail("updated@email.com");
        LocalDateTime localNow = LocalDateTime.now();

        testUser.setUpdatedAt(localNow);

        // Update the User in the repository
        User updatedUser = userRepository.save(testUser);

        // Assert that the User is updated successfully
        Assertions.assertThat(updatedUser).isNotNull();
        Assertions.assertThat(updatedUser.getUserName()).isEqualTo("updatedUserName");
        Assertions.assertThat(updatedUser.getFirstName()).isEqualTo("updatedFirstName");
        Assertions.assertThat(updatedUser.getLastName()).isEqualTo("updatedLastName");
        Assertions.assertThat(updatedUser.getEmail()).isEqualTo("updated@email.com");
        Assertions.assertThat(updatedUser.getUpdatedAt()).isEqualTo(localNow);

    }

    /**
     * test delete()
     */
    @Test
    void UserRepository_DeleteUser() {
        // Create and save a test User
        User testUser = new User("testUsername", "testFirstname", "testLastname", "test@email.com");
        userRepository.save(testUser);

        // Delete the User from the repository
        userRepository.delete(testUser);

        // Try to retrieve the deleted User
        User deletedUser = userRepository.findById(testUser.getId()).orElse(null);

        // Assert that the User is deleted successfully
        Assertions.assertThat(deletedUser).isNull();
    }

}