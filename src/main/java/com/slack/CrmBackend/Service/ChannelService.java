package com.slack.CrmBackend.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slack.CrmBackend.model.Channel;
import com.slack.CrmBackend.repository.ChannelRepository;

/** ChannelService */
@Service
public class ChannelService {

  @Autowired
  ChannelRepository uRepository;

  /**
   * @return all Channel found
   */
  public List<Channel> getAllChannels() {
    return uRepository.findAll();
  }

  /**
   * @param id id of channel to find
   * @return channel found
   */
  public Optional<Channel> getChannelById(Integer id) {
    return uRepository.findById(id);
  }

  /**
   * @param nChannel new channel to create
   * @return channel saved
   */
  public Channel createChannel(Channel nChannel) {
    return uRepository.save(nChannel);
  }

  /**
   * @param updateChannel Data of channel uupdated
   * @return saved channel or null
   */
  public Channel updateChannel(Channel updateChannel) {
    return uRepository.save(updateChannel);
  }

  /**
   * @param channelId Id of channel to delete
   */
  public void deleteChannel(Integer channelId) {
    uRepository.deleteById(channelId);
  }
}
