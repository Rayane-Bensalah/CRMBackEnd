package com.slack.CrmBackend.Service;

import com.slack.CrmBackend.model.Channel;
import com.slack.CrmBackend.repository.ChannelRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** ChannelService */
@Service
public class ChannelService {

  @Autowired ChannelRepository uRepository;

  /**
   * @return all Channel found
   */
  public List<Channel> getAllChannel() {
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

  //
  /**
   * @param channelId id of channelr to update
   * @param updateChannel Data of channel uupdated
   * @return saved channel or null
   */
  public Channel updateChannel(Integer channelId, Channel updateChannel) {
    Optional<Channel> eChannel = uRepository.findById(channelId);
    if (eChannel.isPresent()) {
      updateChannel.setId(channelId);
      return uRepository.save(updateChannel);
    } else {
      return null;
    }
  }

  /**
   * @param channelId Id of channelr to delete
   */
  public void deletChannel(Integer channelId) {
    uRepository.deleteById(channelId);
  }
}
