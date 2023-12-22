package com.slack.CrmBackend.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.slack.CrmBackend.Service.UserService;
import com.slack.CrmBackend.model.User;

/**
 * Tests for UserServices
 * Use of @Transactional annotation causes the test to be run within a
 * transaction that is, by default, automatically rolled back after completion
 * of the test.
 */
@Transactional
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2) // Simulate a Database
@SpringBootTest
class UserServicesTests {

    @Autowired
    UserService userService = Mockito.mock(UserService.class);

    /**
     * test getAllUser()
     */
    @Test
    public void UserService_GetAllUsers() {

        // Create and save 2 Users
        User testUser1 = new User("testUsername1", "testFirstname1", "testLastname1", "test@email1.com");
        User testUser2 = new User("testUsername2", "testFirstname2", "testLastname2", "test@email2.com");

        userService.createUser(testUser1);
        userService.createUser(testUser2);

        // Get all Users
        List<User> UserList = userService.getAllUsers();

        // Assert both Users are created and saved
        Assertions.assertThat(UserList).isNotNull();
        Assertions.assertThat(UserList.size()).isEqualTo(2);
    }

    /**
     * test getUserById
     */
    @Test
    public void UserService_GetUserById() {
        // Create and save 2 Users
        User testUser1 = new User("testUsername", "testFirstname", "testLastname", "test@email.com");

        userService.createUser(testUser1);

        // Get one User
        Optional<User> foundUser = userService.getUserById(testUser1.getId());

        // Assert user is found and correct
        Assertions.assertThat(foundUser).isNotNull();
        Assertions.assertThat(foundUser).isPresent();
        Assertions.assertThat(foundUser.get().getFirstName()).isEqualTo(testUser1.getFirstName());
    }

    /**
     * test createUser()
     */
    @Test
    void UserService_CreateUser() {

        // Create test User and save it
        User testUser = new User("testUsername", "testFirstname", "testLastname", "test@email.com");
        userService.createUser(testUser);

        // Assert User is created and with correct id
        Assertions.assertThat(testUser).isNotNull();
        Assertions.assertThat(testUser.getId()).isGreaterThan(0);
    }

    /**
     * test update
     */
    @Test
    void UserService_UpdateUser() {
        // Create and save a test User
        User testUser = new User("testUsername", "testFirstname", "testLastname", "test@email.com");
        userService.createUser(testUser);

        LocalDateTime localNow = LocalDateTime.now();

        // Modify the User's properties
        testUser.setUserName("updatedUserName");
        testUser.setFirstName("updatedFirstName");
        testUser.setLastName("updatedLastName");
        testUser.setEmail("updated@email.com");
        testUser.setUpdatedAt(localNow);

        // Update the User in the repository
        User updatedUser = userService.updateUser(testUser);

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
    void UserService_DeleteUser() {
        // Create and save a test User
        User testUser = new User("testUsername", "testFirstname", "testLastname", "test@email.com");
        userService.createUser(testUser);

        // Delete the User from the repository
        userService.deleteUser(testUser);

        // Try to retrieve the deleted User
        User deletedUser = userService.getUserById(testUser.getId()).orElse(null);

        // Assert that the User is deleted successfully
        Assertions.assertThat(deletedUser).isNull();
    }

}