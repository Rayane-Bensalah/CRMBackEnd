package com.slack.CrmBackend.Service;

import com.slack.CrmBackend.model.User;
import com.slack.CrmBackend.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** UserService */
@Service
public class UserService {

  @Autowired UserRepository uRepository;

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
    return uRepository.save(nUser);
  }

  /**
   * @param userId id of user to update
   * @param updateUser Data of user uupdated
   * @return saved user or null
   */
  public User updateUser(Integer userId, User updateUser) {
    Optional<User> eUser = uRepository.findById(userId);
    if (eUser.isPresent()) {
      updateUser.setId(userId);
      return uRepository.save(updateUser);
    } else {
      return null;
    }
  }

  /**
   * @param userId Id of user to delete
   */
  public void deleteUser(Integer userId) {
    uRepository.deleteById(userId);
  }
}
