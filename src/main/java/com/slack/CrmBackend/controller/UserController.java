package com.slack.CrmBackend.controller;

import com.slack.CrmBackend.Service.UserService;
import com.slack.CrmBackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * Controller for User Entities, manages REST API
 * Utilizes Spring annotations for request mappings and dependency injection.
 * Provides CRUD operations: get all, get one, adding, updating, and deleting users.
 */
@RestController
@RequestMapping("user")
public class UserController {

    /**
     * Autowiring the UserService for handling business logic related to users.
     */
    @Autowired
    UserService userService;

    /**
     * Endpoint to retrieve all users.
     * @return List<Users>
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Endpoint to retrieve a user by its ID.
     * Use Optional to handle the possibility of a null result.
     * Return a response with the user if it exists.
     * Return a not found response if the user doesn't exist.
     * @param Integer id
     * @return ResponseEntity(User)
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id){
        Optional<User> optional = userService.getUserById(id);
        if(optional.isPresent()){
            User user = optional.get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to add a new user.
     * Call the service to create a new user.
     * Return a response with the created user.
     * @param User newUser
     * @return ResponseEntity(User newUser)
     */
    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    public ResponseEntity<User> addUser(@RequestBody User newUser){
        userService.createUser(newUser);
        return ResponseEntity.ok(newUser);
    }

    /**
     * Endpoint to update an existing user.
     * Check if the user with the given ID exists.
     * Set the ID and call the service to update the user.
     * Return a response with the updated user.
     * Return a not found response if the user doesn't exist.
     * @param Integer id
     * @param User user
     * @return ResponseEntity(User updatedUser)
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> putUser(@PathVariable Integer id, @RequestBody User user) {
        Optional<User> existingUser = userService.getUserById(id);

        if (existingUser.isPresent()) {
            user.setId(id);
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
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
     * @param Integer id
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Integer id){
        Optional<User> optional = userService.getUserById(id);
        if(optional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        }
    }
}
