package com.slack.CrmBackend.Service;

import com.slack.CrmBackend.model.Message;
import com.slack.CrmBackend.repository.MessageRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** MessageService */
@Service
public class MessageService {
  @Autowired MessageRepository mRepository;

  /**
   * @return all Message found
   */
  public List<Message> getAllmessage() {
    return mRepository.findAll();
  }

  /**
   * @param id id of Message to find
   * @return Message found
   */
  public Optional<Message> getmessageById(Integer id) {
    return mRepository.findById(id);
  }

  /**
   * @param nMessage new message to create
   * @return Message saved
   */
  public Message createmessage(Message nMessage) {
    return mRepository.save(nMessage);
  }

  //
  /**
   * @param MessageId id of messager to update
   * @param updateMessage Data of message uupdated
   * @return saved Message or null
   */
  public Message updatemessage(Integer messageId, Message updateMessage) {
    Optional<Message> eMessage = mRepository.findById(messageId);
    if (eMessage.isPresent()) {
      updateMessage.setId(messageId);
      return mRepository.save(updateMessage);
    } else {
      return null;
    }
  }

  /**
   * @param MessageId Id of messager to delete
   */
  public void deletMessage(Integer messageId) {
    mRepository.deleteById(messageId);
  }

  public MessageRepository getmRepository() {
    return mRepository;
  }
}
