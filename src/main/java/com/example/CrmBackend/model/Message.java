package com.example.CrmBackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/** Message */
@Entity
@Table(name = "messages")
public class Message {

  /** Id of user */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** Content of the message */
  @Column(name = "content")
  private String content;

  /** User id of the message */
  @Column(name = "user_id")
  Integer userId;

  @Column(name = "channel_id")
  Integer channelId;

  @Column(name = "send_date")
  LocalDateTime sendDate;

  public Message() {}

  /**
   * @param content Content of message
   * @param userId User id
   * @param channelId Channel id
   */
  public Message(String content, Integer userId, Integer channelId) {
    content = this.content;
    userId = this.userId;
    channelId = this.channelId;
    sendDate = LocalDateTime.now();
  }

  /**
   * @return id
   */
  public Integer getId() {
    return id;
  }

  /**
   * @param id id of message
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * @return content of message
   */
  public String getContent() {
    return content;
  }

  /**
   * @param content content of message
   */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * @return id
   */
  public Integer getUserId() {
    return userId;
  }

  /**
   * @param userId User id
   */
  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  /**
   * @return channel id
   */
  public Integer getChannelId() {
    return channelId;
  }

  /**
   * @param channelId set the channel id
   */
  public void setChannelId(Integer channelId) {
    this.channelId = channelId;
  }

  /**
   * @return Date of the message send
   */
  public LocalDateTime getSendDate() {
    return sendDate;
  }

  /**
   * @param sendDate TimeStamp of the message
   */
  public void setSendDate(LocalDateTime sendDate) {
    this.sendDate = sendDate;
  }
}
