package com.slack.CrmBackend.controller;

import com.slack.CrmBackend.Service.MessageService;
import com.slack.CrmBackend.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

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

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    public ResponseEntity<Message> addMessage(@RequestBody Message newMessage){
        messageService.createMessage(newMessage);
        return ResponseEntity.ok(newMessage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> putMessage(@PathVariable Integer id, @RequestBody Message message) {
        Optional<Message> existingMessage = messageService.getMessageById(id);

        if (existingMessage.isPresent()) {
            message.setId(id);
            Message updatedMessage = messageService.createMessage(message);
            return ResponseEntity.ok(updatedMessage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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
