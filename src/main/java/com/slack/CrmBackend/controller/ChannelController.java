package com.slack.CrmBackend.controller;

import com.slack.CrmBackend.Service.ChannelService;
import com.slack.CrmBackend.model.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("channel")
public class ChannelController {

    @Autowired
    ChannelService channelService;

    @GetMapping
    public List<Channel> getAllChannels() {
        return channelService.getAllChannels();
    }

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

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    public ResponseEntity<Channel> addChannel(@RequestBody Channel newChannel){
        channelService.createChannel(newChannel);
        return ResponseEntity.ok(newChannel);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity deleteChannel(@PathVariable("id") Integer id){
        Optional<Channel> optional = channelService.getChannelById(id);
        if(optional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else {
            channelService.deleteChannel(id);
            return ResponseEntity.ok().build();
        }
    }
}
