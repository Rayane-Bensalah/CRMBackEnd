package com.example.CrmBackend.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/** Channel */
@Entity
@Table(name = "channels")
public class Channel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "is_main")
  private boolean isMain;

  @Column(name = "created_at")
  LocalDateTime createdAt;

  /* Channel messages */
  @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Message> messages = new ArrayList<>();

  /** Empty constructor */
  public Channel() {
  }

  /**
   * @param name   Name of the channel
   * @param isMain boolean value to determine if the channel is the main channel
   */
  public Channel(String name, boolean isMain) {
    name = this.name;
    isMain = this.isMain;
  }

  /**
   * @return id of Channel
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id set id of channel
   */
  public void setId(Long id) {
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
}
