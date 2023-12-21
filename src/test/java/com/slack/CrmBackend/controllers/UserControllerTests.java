package com.slack.CrmBackend.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slack.CrmBackend.Service.UserService;
import com.slack.CrmBackend.model.User;

/**
 * Tests for UserController
 * tested methods getAllUsers(), getUserById(), addUser(), putUser() and
 * deleteUser(),
 * used Mockito with MockMvc to mock an API Call on the controller and check the
 * excepted JSON result
 */
@Transactional
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTests {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Test getAllUsers() : Verify that the get correctly get all existing users
     * 
     * @throws Exception
     */
    @Test
    void getAllUsers() throws Exception {

        User user1 = new User("testUserName1", "testFirstName1", "testLastName1", "test@email1.com");
        User user2 = new User("testUserName2", "testFirstName2", "testLastName2", "test@email2.com");
        Mockito.when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$[0].userName").value("testUserName1"))
                .andExpect(jsonPath("$[0].firstName").value("testFirstName1"))
                .andExpect(jsonPath("$[0].lastName").value("testLastName1"))
                .andExpect(jsonPath("$[0].email").value("test@email1.com"))

                .andExpect(jsonPath("$[1].userName").value("testUserName2"))
                .andExpect(jsonPath("$[1].firstName").value("testFirstName2"))
                .andExpect(jsonPath("$[1].lastName").value("testLastName2"))
                .andExpect(jsonPath("$[1].email").value("test@email2.com"));
    }

    /**
     * Test getUserById() : Verify that the get with endpoint /users/{id} correctly
     * get an existing user
     * 
     * @throws Exception
     */
    @Test
    void getUserById() throws Exception {

        User user = new User("testUserName", "testFirstName", "testLastName", "test@email.com");
        Mockito.when(userService.getUserById(1)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userName").value("testUserName"))
                .andExpect(jsonPath("$.firstName").value("testFirstName"))
                .andExpect(jsonPath("$.lastName").value("testLastName"))
                .andExpect(jsonPath("$.email").value("test@email.com"));
    }

    /**
     * Test addUser() : Verify that the add correctly create a new user
     * 
     * @throws Exception
     */
    @Test
    void addUser() throws Exception {
        User user = new User("testUserName", "testFirstName", "testLastName", "test@email.com");
        Mockito.when(userService.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userName").value("testUserName"))
                .andExpect(jsonPath("$.firstName").value("testFirstName"))
                .andExpect(jsonPath("$.lastName").value("testLastName"))
                .andExpect(jsonPath("$.email").value("test@email.com"));
    }

    /**
     * Test putUser() : Verify that the put with endpoint /users/{id} correctly
     * update an existing user
     * 
     * @throws Exception
     */
    @Test
    void putUser() throws Exception {
        // Mocking an existing user
        User existingUser = new User("existingUser", "Existing", "User", "existing@email.com");
        userService.createUser(existingUser);

        User updatedUser = new User("updatedUser", "Updated", "User", "updated@email.com");

        // Mocking the service behavior
        Mockito.when(userService.getUserById(1)).thenReturn(Optional.of(existingUser));
        Mockito.when(userService.updateUser(any(User.class))).thenReturn(existingUser); // Returning the existing
                                                                                        // user after update

        mockMvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userName").value("existingUser")) // Checking the existing user's fields
                .andExpect(jsonPath("$.firstName").value("Existing"))
                .andExpect(jsonPath("$.lastName").value("User"))
                .andExpect(jsonPath("$.email").value("existing@email.com"));
    }

    /**
     * Test deleteUser() : Verify that the delete with endpoint /users/{id}
     * correctly
     * delete an existing user
     * 
     * @throws Exception
     */
    @Test
    void deleteUser() throws Exception {
        User userToDelete = new User("testUserName1", "testFirstName1", "testLastName1", "test@email1.com");
        userToDelete.setId(1);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNotFound());
    }
}