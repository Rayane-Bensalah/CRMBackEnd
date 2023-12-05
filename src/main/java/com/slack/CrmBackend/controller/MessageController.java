package com.slack.CrmBackend.controller;

import com.slack.CrmBackend.Service.MessageService;
import com.slack.CrmBackend.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * Controller for Message Entities, manages REST API
 * Utilizes Spring annotations for request mappings and dependency injection.
 * Provides CRUD operations: get all, get one, adding, updating, and deleting messages.
 */
@RestController
@RequestMapping("message")
public class MessageController {

    /**
     * Autowiring the MessageService for handling business logic related to messages.
     */
    @Autowired
    MessageService messageService;

    /**
     * Endpoint to retrieve all messages.
     * @return List<Messages>
     */
    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    /**
     * Endpoint to retrieve a message by its ID.
     * Use Optional to handle the possibility of a null result.
     * Return a response with the message if it exists.
     * Return a not found response if the message doesn't exist.
     * @param Integer id
     * @return ResponseEntity(Message message)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable("id") Integer id){
        Optional<Message> optional = messageService.getMessageById(id);
        if(optional.isPresent()){
            Message message = optional.get();
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to add a new message.
     * Call the service to create a new message.
     * Return a response with the created message.
     * @param Message newMessage
     * @return ResponseEntity(Message newMessage)
     */
    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    public ResponseEntity<Message> addMessage(@RequestBody Message newMessage){
        messageService.createMessage(newMessage);
        return ResponseEntity.ok(newMessage);
    }

    /**
     * Endpoint to update an existing message.
     * Check if the message with the given ID exists.
     * Set the ID and call the service to update the message.
     * Return a response with the updated message.
     * Return a not found response if the message doesn't exist.
     * @param Integer id
     * @param Message message
     * @return ResponseEntity(Message updatedMessage)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Message> putMessage(@PathVariable Integer id, @RequestBody Message message) {
        Optional<Message> existingMessage = messageService.getMessageById(id);

        if (existingMessage.isPresent()) {
            message.setId(id);
            Message updatedMessage = messageService.updateMessage(id, message);
            return ResponseEntity.ok(updatedMessage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to delete a message by its ID.
     * Check if the message with the given ID exists.
     * Return a not found response if the message doesn't exist.
     * Call the service to delete the message.
     * Return a response indicating successful deletion.
     * @param Integer id
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteMessage(@PathVariable("id") Integer id){
        Optional<Message> optional = messageService.getMessageById(id);
        if(optional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else {
            messageService.deleteMessage(id);
            return ResponseEntity.ok().build();
        }
    }
}
