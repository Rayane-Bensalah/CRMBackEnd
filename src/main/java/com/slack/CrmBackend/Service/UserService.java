package com.slack.CrmBackend.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slack.CrmBackend.model.User;
import com.slack.CrmBackend.repository.UserRepository;

/** UserService */
@Service
public class UserService {

  @Autowired
  UserRepository uRepository;

  /**
   * @return all User found
   */
  public List<User> getAllUsers() {
    return uRepository.findAll();
  }

  /**
   * @param id id of user to find
   * @return user found
   */
  public Optional<User> getUserById(Integer id) {
    return uRepository.findById(id);
  }

  /**
   * @param nUser new user to create
   * @return user saved
   */
  public User createUser(User nUser) {

    if (!uRepository.existsByUserName(nUser.getUserName())) {
      return uRepository.save(nUser);
    } else {
      return uRepository.findByUserName(nUser.getUserName());
    }
  }

  /**
   * @param updateUser Data of user uupdated
   * @return saved user or null
   */
  public User updateUser(User updateUser) {
    return uRepository.save(updateUser);
  }

  /**
   * @param userId Id of user to delete
   */
  public void deleteUser(User user) {
    uRepository.deleteById(user.getId());
  }
}
