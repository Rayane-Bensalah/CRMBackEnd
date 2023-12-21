package com.slack.CrmBackend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.slack.CrmBackend.Service.UserService;
import com.slack.CrmBackend.dto.UserDto;
import com.slack.CrmBackend.dto.UserPostResquestDto;
import com.slack.CrmBackend.dto.mapper.UserMapper;
import com.slack.CrmBackend.model.User;

/**
 * Controller for User Entities, manages REST API
 * Utilizes Spring annotations for request mappings and dependency injection.
 * Provides CRUD operations: get all, get one, adding, updating, and deleting
 * users.
 */
@RestController
@RequestMapping("users")
public class UserController {

    /**
     * Autowiring the UserService for handling business logic related to users.
     */
    @Autowired
    UserService userService;

    /**
     * Autowiring UserMapper for transferring data between services
     */
    @Autowired
    UserMapper userMapper;

    /**
     * Endpoint to retrieve all users.
     * 
     * @return List<Users>
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userMapper.usersToUsersDto(userService.getAllUsers());
    }

    /**
     * Endpoint to retrieve a user by its ID.
     * Use Optional to handle the possibility of a null result.
     * Return a response with the user if it exists.
     * Return a not found response if the user doesn't exist.
     * 
     * @param @PathVariable Integer id
     * @return ResponseEntity(User)
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Integer id) {
        Optional<User> optional = userService.getUserById(id);
        if (optional.isPresent()) {
            User user = optional.get();
            return ResponseEntity.ok(userMapper.userToDto(user));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to add a new user.
     * Call the service to create a new user.
     * Return a response with the created user.
     * 
     * @param @RequestBody UserDto
     * @return ResponseEntity(User newUser)
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> addUser(@RequestBody UserPostResquestDto userPostResquestDto) {
        User user = userService.createUser(userMapper.userPostResquestDtoToUser(userPostResquestDto));
        return ResponseEntity.ok(userMapper.userToDto(user));
    }

    /**
     * Endpoint to update an existing user.
     * Check if the user with the given ID exists.
     * Set the ID and call the service to update the user.
     * Return a response with the updated user.
     * Return a not found response if the user doesn't exist.
     * 
     * @param @PathVariable Integer id
     * @param @RequestBody  UserDto userDto
     * @return ResponseEntity(UserDto)
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> putUser(@PathVariable Integer id, @RequestBody UserDto userDto) {
        Optional<User> existingUser = userService.getUserById(id);

        if (existingUser.isPresent()) {
            User updatedUser = userService.updateUser(this.convert(userDto, existingUser.get()));
            return ResponseEntity.ok(userMapper.userToDto(updatedUser));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to delete a user by its ID.
     * Check if the user with the given ID exists.
     * Return a not found response if the user doesn't exist.
     * Call the service to delete the user.
     * Return a response indicating successful deletion.
     * 
     * @param @PathVariable Integer id
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id) {
        Optional<User> optional = userService.getUserById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        }
    }

    private User convert(UserDto userDto, User user) {
        if (userDto.getUserName() != null) {
            user.setUserName(userDto.getUserName());
        }

        if (userDto.getFirstName() != null) {
            user.setFirstName(userDto.getFirstName());
        }

        if (userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }

        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }

        return user;
    }
}
