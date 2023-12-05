package com.slack.CrmBackend.controller;

import com.slack.CrmBackend.Service.ChannelService;
import com.slack.CrmBackend.model.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * Controller for Channel Entities, manages REST API
 * Utilizes Spring annotations for request mappings and dependency injection.
 * Provides CRUD operations : get all, get one, adding, updating, and deleting channels.
 */
@RestController
@RequestMapping("channel")
public class ChannelController {

    /**
     * Autowiring the ChannelService for handling business logic related to messages.
     */
    @Autowired
    ChannelService channelService;

    /**
     * Endpoint to retrieve all channels.
     * @return List<Channels>
     */
    @GetMapping
    public List<Channel> getAllChannels() {
        return channelService.getAllChannels();
    }

    /**
     * Endpoint to retrieve a chanel by its ID.
     * Use Optional to handle the possibility of a null result.
     * Return a response with the channel if it exists.
     * Return a not found response if the channel doesn't exist.
     * @param Integer id
     * @return ResponseEntity(Channel channel)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Channel> getChannelById(@PathVariable("id") Integer id){

        Optional<Channel> optional = channelService.getChannelById(id);

        if(optional.isPresent()){
            Channel channel = optional.get();
            return ResponseEntity.ok(channel);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to add a new channel.
     * Call the service to create a new channel.
     * Return a response with the created channel.
     * @param Channel newChannel
     * @return ResponseEntity(Channel newChannel)
     */
    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    public ResponseEntity<Channel> addChannel(@RequestBody Channel newChannel){
        channelService.createChannel(newChannel);
        return ResponseEntity.ok(newChannel);
    }

    /**
     * Endpoint to update an existing channel.
     * Check if the channel with the given ID exists.
     * Set the ID and call the service to update the channel.
     * Return a response with the updated channel.
     * Return a not found response if the channel doesn't exist.
     * @param Integer id
     * @param Channel channel
     * @return ResponseEntity
     */
    @PutMapping("/{id}")
    public ResponseEntity<Channel> putChannel(@PathVariable Integer id, @RequestBody Channel channel) {

        Optional<Channel> existingChannel = channelService.getChannelById(id);

        if (existingChannel.isPresent()) {
            channel.setId(id);
            Channel updatedChannel = channelService.updateChannel(id, channel);

            return ResponseEntity.ok(updatedChannel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to delete a channel by its ID.
     * Check if the channel with the given ID exists.
     * Return a not found response if the channel doesn't exist.
     * Call the service to delete the message.
     * Return a response indicating successful deletion.
     * @param Integer id
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteChannel(@PathVariable("id") Integer id){

        Optional<Channel> optional = channelService.getChannelById(id);

        if(optional.isEmpty()){
            return ResponseEntity.notFound().build();

        } else {
            channelService.deleteChannel(id);
            return ResponseEntity.ok().build();
        }
    }
}
