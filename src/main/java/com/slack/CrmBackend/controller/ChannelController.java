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

import com.slack.CrmBackend.Service.ChannelService;
import com.slack.CrmBackend.dto.ChannelDto;
import com.slack.CrmBackend.dto.mapper.ChannelMapper;
import com.slack.CrmBackend.model.Channel;

/**
 * Controller for Channel Entities, manages REST API
 * Utilizes Spring annotations for request mappings and dependency injection.
 * Provides CRUD operations : get all, get one, adding, updating, and deleting
 * channels.
 */
@RestController
@RequestMapping("channels")
public class ChannelController {

    /**
     * Autowiring the ChannelService for handling business logic related to
     * messages.
     */
    @Autowired
    ChannelService channelService;

    /**
     * Autowiring ChannelMapper for transferring data between services
     */
    @Autowired
    ChannelMapper channelMapper;

    /**
     * Endpoint to retrieve all channels
     * 
     * @return List<ChannelDto>
     */
    @GetMapping
    public List<ChannelDto> getAllChannels() {
        return channelMapper.channelsToDto(channelService.getAllChannels());
    }

    /**
     * Endpoint to retrieve a chanel by its ID.
     * Use Optional to handle the possibility of a null result.
     * Return a response with the channel if it exists.
     * Return a not found response if the channel doesn't exist.
     * 
     * @param @PathVariable Integer id
     * @return ResponseEntity(ChannelDto)
     */
    @GetMapping("/{id}")
    public ResponseEntity<ChannelDto> getChannelById(@PathVariable("id") Integer id) {

        Optional<Channel> optional = channelService.getChannelById(id);

        if (optional.isPresent()) {
            Channel channel = optional.get();
            return ResponseEntity.ok(channelMapper.channelToDto(channel));

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to add a new channel.
     * Call the service to create a new channel.
     * Return a response with the created channel.
     * 
     * @param @RequestBody ChannelDto channelDto
     * @return ResponseEntity(ChannelDto)
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ChannelDto> addChannel(@RequestBody ChannelDto channelDto) {
        Channel channel = channelService.createChannel(channelMapper.channelDtoToChannel(channelDto));
        return ResponseEntity.ok(channelMapper.channelToDto(channel));
    }

    /**
     * Endpoint to update an existing channel.
     * Check if the channel with the given ID exists.
     * Set the ID and call the service to update the channel.
     * Return a response with the updated channel.
     * Return a not found response if the channel doesn't exist.
     * 
     * @param @PathVariable Integer id
     * @param @RequestBody  ChannelDto channelDto
     * @return ResponseEntity
     */
    @PutMapping("/{id}")
    public ResponseEntity<ChannelDto> putChannel(@PathVariable Integer id, @RequestBody ChannelDto channelDto) {

        Optional<Channel> existingChannel = channelService.getChannelById(id);

        if (existingChannel.isPresent()) {
            channelDto.setId(id);
            Channel updatedChannel = channelService.updateChannel(id, channelMapper.channelDtoToChannel(channelDto));
            return ResponseEntity.ok(channelMapper.channelToDto(updatedChannel));
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
     * 
     * @param @PathVariable Integer id
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChannel(@PathVariable("id") Integer id) {

        Optional<Channel> optional = channelService.getChannelById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();

        } else {
            channelService.deleteChannel(id);
            return ResponseEntity.ok().build();
        }
    }
}
