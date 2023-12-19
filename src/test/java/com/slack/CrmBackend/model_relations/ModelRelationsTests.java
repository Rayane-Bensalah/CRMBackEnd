package com.slack.CrmBackend.model_relations;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.slack.CrmBackend.model.Channel;
import com.slack.CrmBackend.model.Message;
import com.slack.CrmBackend.model.User;
import com.slack.CrmBackend.repository.ChannelRepository;
import com.slack.CrmBackend.repository.MessageRepository;
import com.slack.CrmBackend.repository.UserRepository;

import jakarta.transaction.Transactional;

/**
 * Tests for Model Relations
 * tested save() and findAll()
 */
/**
 * Tests for Model Relations
 * Use of @Transactional annotation causes the test to be run within a
 * transaction that is, by default, automatically rolled back after completion
 * of the test.
 */
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
@SpringBootTest
@Transactional
class ModelRelationsTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChannelRepository channelRepository;

    @Autowired
    MessageRepository messageRepository;

    /**
     * Logger : Display custom datas into console
     */
    private static Logger logger = LoggerFactory.getLogger(ModelRelationsTests.class);

    @Test
    void userMessagesRelationsTests() {
        logger.info(
                "############### [ModelRelationsTests][userMessagesRelationsTests] ###############");

        /**
         * Create 2 Users
         */
        User user1 = new User("userName1", "firstName1", "lastName1", "user1@mmail.com");
        User user2 = new User("userName2", "firstName2", "lastName2", "user2@mmail.com");
        userRepository.save(user1);
        userRepository.save(user2);

        logger.info("######1######## [User1] : " + user1.getId() + " ###############");
        logger.info("######1######## [User2] : " + user2.getId() + " ###############");

        /**
         * Create Main Channel
         */
        Channel mainChannel = new Channel("General", true);
        channelRepository.save(mainChannel);

        /**
         * Add Users messages to main Channel
         */
        Message message1 = new Message("Hello", user1, mainChannel);
        messageRepository.save(message1);
        Message message2 = new Message("Comment allez-vous ?", user1, mainChannel);
        messageRepository.save(message2);
        Message message3 = new Message("Très bien et vous ?", user2, mainChannel);
        messageRepository.save(message3);

        /**
         * Check User 1
         */
        logger.info("######2######## [User1] : " + user1.getId() + " ###############");
        logger.info("######2######## [User2] : " + user2.getId() + " ###############");
        Assertions.assertThat(user1).isNotNull();
        Assertions.assertThat(user1.getId()).isEqualTo(1);

        /**
         * Check User 2
         */
        Assertions.assertThat(user2).isNotNull();
        Assertions.assertThat(user2.getId()).isEqualTo(2);

        /**
         * Check Main Channel
         */
        Assertions.assertThat(mainChannel).isNotNull();
        Assertions.assertThat(mainChannel.isMain()).isTrue();

        /**
         * Check User 1 Messages
         * 1. First we load user message from main channel
         * 2. then we check messages
         */
        user1.setMessages(messageRepository.findByUserAndChannel(user1, mainChannel));

        List<Message> user1Messages = user1.getMessages();

        Assertions.assertThat(user1Messages).isNotNull();
        Assertions.assertThat(user1Messages.size()).isEqualTo(2);

        Assertions.assertThat(user1Messages.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(user1Messages.get(0).getContent()).isEqualTo("Hello");
        Assertions.assertThat(user1Messages.get(1).getContent()).isEqualTo("Comment allez-vous ?");

        /**
         * Check User 2 Message
         * 1. First we load user message from main channel
         * 2. then we check messages
         */
        user2.setMessages(messageRepository.findByUserAndChannel(user2, mainChannel));

        List<Message> user2Messages = user2.getMessages();

        Assertions.assertThat(user2Messages).isNotNull();
        Assertions.assertThat(user2Messages.size()).isEqualTo(1);
        Assertions.assertThat(user2Messages.get(0).getContent()).isEqualTo("Très bien et vous ?");
    }

}