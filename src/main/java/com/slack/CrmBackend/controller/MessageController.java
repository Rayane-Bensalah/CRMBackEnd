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

import com.slack.CrmBackend.Service.MessageService;
import com.slack.CrmBackend.dto.MessageDto;
import com.slack.CrmBackend.dto.MessagePostResquestDto;
import com.slack.CrmBackend.dto.mapper.MessageMapper;
import com.slack.CrmBackend.model.Message;

/**
 * Controller for Message Entities, manages REST API
 * Utilizes Spring annotations for request mappings and dependency injection.
 * Provides CRUD operations: get all, get one, adding, updating, and deleting
 * messages.
 */
@RestController
@RequestMapping("messages")
public class MessageController {

    /**
     * Autowiring the MessageService for handling business logic related to
     * messages.
     */
    @Autowired
    MessageService messageService;

    /**
     * Autowiring MessageMapper for transferring data between services
     */
    @Autowired
    MessageMapper messageMapper;

    /**
     * Endpoint to retrieve all messages.
     * 
     * @return List<Messages>
     */
    @GetMapping
    public List<MessageDto> getAllMessages() {
        return messageMapper.messagesToDto(messageService.getAllMessages());
    }

    /**
     * Endpoint to retrieve a message by its ID.
     * Use Optional to handle the possibility of a null result.
     * Return a response with the message if it exists.
     * Return a not found response if the message doesn't exist.
     * 
     * @param @PathVariable Integer id
     * @return ResponseEntity(MessageDto)
     */
    @GetMapping("/{id}")
    public ResponseEntity<MessageDto> getMessageById(@PathVariable("id") Integer id) {
        Optional<Message> optional = messageService.getMessageById(id);
        if (optional.isPresent()) {
            Message message = optional.get();
            return ResponseEntity.ok(messageMapper.messageToDto(message));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to add a new message.
     * Call the service to create a new message.
     * Return a response with the created message.
     * 
     * @param @RequestBody Message newMessage
     * @return ResponseEntity(Message newMessage)
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageDto> addMessage(@RequestBody MessagePostResquestDto messagePostResquestDto) {
        Message message = messageService.createMessage(messageMapper.messagePostResquestDtoToMessage(messagePostResquestDto));
        return ResponseEntity.ok(messageMapper.messageToDto(message));
    }

    /**
     * Endpoint to update an existing message.
     * Check if the message with the given ID exists.
     * Set the ID and call the service to update the message.
     * Return a response with the updated message.
     * Return a not found response if the message doesn't exist.
     * 
     * @param @PathVariable Integer id
     * @param @RequestBody  MessageDto messageDto
     * @return ResponseEntity(MessageDto)
     */
    @PutMapping("/{id}")
    public ResponseEntity<MessageDto> putMessage(@PathVariable Integer id, @RequestBody MessageDto messageDto) {
        Optional<Message> existingMessage = messageService.getMessageById(id);

        if (existingMessage.isPresent()) {
            messageDto.setId(id);
            Message updatedMessage = messageService.updateMessage(id, messageMapper.messageDtoToMessage(messageDto));
            return ResponseEntity.ok(messageMapper.messageToDto(updatedMessage));
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
     * 
     * @param Integer id
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable("id") Integer id) {
        Optional<Message> optional = messageService.getMessageById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            messageService.deleteMessage(id);
            return ResponseEntity.ok().build();
        }
    }
}
