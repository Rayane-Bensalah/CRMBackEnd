package com.slack.CrmBackend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.slack.CrmBackend.Service.MessageService;
import com.slack.CrmBackend.dto.MessageDto;
import com.slack.CrmBackend.dto.mapper.MessageMapper;
import com.slack.CrmBackend.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.slack.CrmBackend.Service.ChannelService;
import com.slack.CrmBackend.dto.ChannelDto;
import com.slack.CrmBackend.dto.ChannelPostResquestDto;
import com.slack.CrmBackend.dto.mapper.ChannelMapper;
import com.slack.CrmBackend.exception.ResourceAlreadyExistsException;
import com.slack.CrmBackend.exception.ResourceNotFoundException;
import com.slack.CrmBackend.model.Channel;

/**
 * Controller for Channel Entities, manages REST API
 * Utilizes Spring annotations for request mappings and dependency injection.
 * Provides CRUD operations : get all, get one, adding, updating, and deleting
 * channels.
 */
@RestController
@RequestMapping("channels")
@CrossOrigin({"http://localhost:4200", "http://127.0.0.1:4200"})
public class ChannelController {

    /**
     * Autowiring the ChannelService for handling business logic related to
     * messages.
     */
    @Autowired
    ChannelService channelService;

    @Autowired
    MessageService messageService;

    /**
     * Autowiring ChannelMapper for transferring data between services
     */
    @Autowired
    ChannelMapper channelMapper;

    @Autowired
    MessageMapper messageMapper;

    /**
     * Endpoint to retrieve all channels
     * 
     * @return List<ChannelDto>
     */
    @GetMapping
    public ResponseEntity<List<ChannelDto>> getAllChannels() {
        List<Channel> channels = new ArrayList<Channel>();
        channelService.getAllChannels().forEach(channels::add);

        if (channels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(channelMapper.channelsToDto(channels), HttpStatus.OK);
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
        Channel channel = channelService.getChannelById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Channel with id = " + id));

        return new ResponseEntity<>(channelMapper.channelToDto(channel), HttpStatus.OK);
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
    public ResponseEntity<ChannelDto> addChannel(@RequestBody ChannelPostResquestDto channelPostResquestDto) {
        Channel channel = channelService
                .createChannel(channelMapper.channelPostResquestDtoToChannel(channelPostResquestDto));

        if (channel != null) {
            return new ResponseEntity<>(channelMapper.channelToDto(channel), HttpStatus.CREATED);
        }

        throw new ResourceAlreadyExistsException("Channel Already exists");
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
        Channel channel = channelService.getChannelById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Channel with id = " + id));

        Channel updatedChannel = channelService.updateChannel(this.convert(channelDto, channel));

        return new ResponseEntity<>(channelMapper.channelToDto(updatedChannel), HttpStatus.OK);
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
        Channel channel = channelService.getChannelById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Channel with id = " + id));

        channelService.deleteChannel(channel);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Endpoint to retrieve all messages by their channelID.
     * Use Optional to handle the possibility of a null result.
     * Return a response with the List of messages if it exists.
     * Return a not found response if the message doesn't exist.
     * @param id
     * @return
     */
    @GetMapping("/{id}/messages")
    public ResponseEntity<List<MessageDto>> getMessagesByChannelId(@PathVariable("id") Integer id) {
        List<Message> channelMessages = new ArrayList<>();
        Optional<List<Message>> optional = messageService.getMessagesChannel(id);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            channelMessages = optional.get();
            return new ResponseEntity<>(messageMapper.messagesToDto(channelMessages), HttpStatus.OK);
        }
    }

    private Channel convert(ChannelDto channelDto, Channel channel) {
        if (channelDto.getName() != null) {
            channel.setName(channelDto.getName());
        }

        return channel;
    }

}
