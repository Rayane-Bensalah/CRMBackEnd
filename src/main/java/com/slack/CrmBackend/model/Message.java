package com.slack.CrmBackend.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

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

  /** User object */
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  /** Channel object */
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "channel_id", nullable = false)
  private Channel channel;

  @Column(name = "send_date")
  LocalDateTime sendDate;

  public Message() {
  }

  /**
   * @param content Content of message
   * @param user    User object
   * @param channel Channel object
   */
  public Message(String content, User user, Channel channel) {
    this.content = content;
    this.user = user;
    this.channel = channel;
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
   * @return user
   */
  public User getUser() {
    return user;
  }

  /**
   * @param user User
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * @return Channel
   */
  public Channel getChannel() {
    return channel;
  }

  /**
   * @param channel set the channel id
   */
  public void setChannel(Channel channel) {
    this.channel = channel;
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

  /**
   * Pre Persist attributs
   * Set default values on entity creation
   */
  @PrePersist
  public void onCreate() {
    this.setSendDate(LocalDateTime.now());
  }

}
