package com.slack.CrmBackend.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

/** Channel */
@Entity
@Table(name = "channels")
public class Channel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name")
  private String name;

  @Column(name = "is_main")
  private boolean isMain;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  /* Channel messages */
  @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
  private List<Message> messages = new ArrayList<>();

  /** Empty constructor */
  public Channel() {
  }

  /**
   * @param name   Name of the channel
   * @param isMain boolean value to determine if the channel is the main channel
   */
  public Channel(String name, boolean isMain) {
    this.name = name;
    this.isMain = isMain;
    createdAt = LocalDateTime.now();
  }

  /**
   * @return id of Channel
   */
  public Integer getId() {
    return id;
  }

  /**
   * @param id set id of channel
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * @return name ofthe channel
   */
  public String getName() {
    return name;
  }

  /**
   * @param name set name of the channel
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return True if main channel
   */
  public boolean isMain() {
    return isMain;
  }

  /**
   * @param isMain bool for main channel
   */
  public void setMain(boolean isMain) {
    this.isMain = isMain;
  }

  /**
   * @return creation date of channel
   */
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * @param createdAt creation date of channel
   */
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * @return Channel messages
   */
  public List<Message> getMessages() {
    return this.messages;
  }

  /**
   * @param List<Message> Channel messages
   */
  public void setMessages(List<Message> messages) {
    this.messages = messages;
  }

  /**
   * Pre Persist attributs
   * Set default values on entity creation
   */
  @PrePersist
  public void onCreate() {
    this.setCreatedAt(LocalDateTime.now());
  }
}
