package com.slack.CrmBackend.model_relations;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.slack.CrmBackend.model.Channel;
import com.slack.CrmBackend.model.Message;
import com.slack.CrmBackend.model.User;
import com.slack.CrmBackend.repository.ChannelRepository;
import com.slack.CrmBackend.repository.MessageRepository;
import com.slack.CrmBackend.repository.UserRepository;

/**
 * Tests for ChannelRepository
 * tested save() and findAll()
 */
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2) // Simulate a Database
@DataJpaTest
class ModelRelationsTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChannelRepository channelRepository;

    @Autowired
    MessageRepository messageRepository;

    private Logger logger = LoggerFactory.getLogger(ModelRelationsTests.class);

    /**
     * test save()
     * 
     * @throws InterruptedException
     */
    @Test
    void userMessagesTests() throws InterruptedException {
        User user1 = new User("userName1", "firstName1", "lastName1", "user1@mmail.com");
        userRepository.save(user1);

        Channel mainChannel = new Channel("General", true);
        channelRepository.save(mainChannel);

        // logger.info("******************* Start | mainChannel *******************");
        // logger.info("mainChannel.getId() => " + mainChannel.getId());
        // logger.info("mainChannel.getName() => " + mainChannel.getName());
        // logger.info("mainChannel.isMain() => " + mainChannel.isMain());
        // logger.info("******************* End | mainChannel *******************");

        Message message1 = new Message("Hello", user1, mainChannel);
        messageRepository.save(message1);
        Message message2 = new Message("Comment allez-vous ?", user1, mainChannel);
        messageRepository.save(message2);

        Assertions.assertThat(user1).isNotNull();
        Assertions.assertThat(user1.getId()).isEqualTo(1);

        Assertions.assertThat(mainChannel).isNotNull();
        Assertions.assertThat(mainChannel.getId()).isEqualTo(1);

        Assertions.assertThat(mainChannel.isMain()).isEqualTo(true);

        logger.info("******************* Start | mainChannel *******************");

        for (Message m : mainChannel.getMessages()) {
            logger.info("Messages Id => " + m.getId());
            logger.info("User => " + m.getUser().getUserName());
        }

        logger.info("******************* End | mainChannel *******************");

        // Assertions.assertThat(mainChannel.getMessages().size()).isEqualTo(2);
    }

}