package com.slack.CrmBackend.controller;

import com.slack.CrmBackend.Service.UserService;
import com.slack.CrmBackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

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

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    public ResponseEntity<User> addUser(@RequestBody User newUser){
        userService.createUser(newUser);
        return ResponseEntity.ok(newUser);
    }

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
