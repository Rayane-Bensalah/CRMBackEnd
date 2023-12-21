package com.slack.CrmBackend.model;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

/** User */
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "user_name", nullable = false)
  private String userName;

  @Column(name = "first_name", nullable = true)
  private String firstName;

  @Column(name = "last_name", nullable = true)
  private String lastName;

  @Column(name = "email", nullable = true)
  private String email;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = true)
  private LocalDateTime updatedAt;

  /** User messages */
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Message> messages = new ArrayList<>();

  public User() {
  }

  /**
   * @param userName  User pseudo
   * @param firstName User first Name
   * @param lastName  User last name
   * @param email     User email
   */
  public User(String userName, String firstName, String lastName, String email) {
    this.userName = userName;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  /**
   * @return User messages
   */
  public List<Message> getMessages() {
    return this.messages;
  }

  /**
   * @param List<Message> User messages
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

  /**
   * Pre Update attributs
   * Set default values on entity update
   */
  @PreUpdate
  public void onUpdate() {
    this.setUpdatedAt(LocalDateTime.now());
  }

  @Override
  public String toString() {
    return "{" +
        " id='" + getId() + "'" +
        ", userName='" + getUserName() + "'" +
        ", firstName='" + getFirstName() + "'" +
        ", lastName='" + getLastName() + "'" +
        ", email='" + getEmail() + "'" +
        ", createdAt='" + getCreatedAt() + "'" +
        ", updatedAt='" + getUpdatedAt() + "'" +
        ", messages='" + getMessages() + "'" +
        "}";
  }
}
