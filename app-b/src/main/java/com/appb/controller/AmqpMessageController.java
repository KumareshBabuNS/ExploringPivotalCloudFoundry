package com.appb.controller;

import com.appb.messaging.dto.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.appb.messaging.MessagingConfiguration.QUEUE_NAME;

/**
 * @author Davor Sauer
 */
@Controller
public class AmqpMessageController {

    private static final Logger logger = LoggerFactory.getLogger(AmqpMessageController.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    @RequestMapping("amqp/send")
    @ResponseBody
    public UUID sendMessage() {
        UUID uuid = UUID.randomUUID();
        logger.info("Sending message: {}", uuid);
        amqpTemplate.convertAndSend(QUEUE_NAME, uuid.toString());

        Message message = new Message(uuid.toString(), LocalDateTime.now().toString());
        logger.info("Sending message 2: {}", uuid);
        amqpTemplate.convertAndSend("test_messages", LocalDateTime.now().toString());


        return uuid;
    }

    @RequestMapping(value = "amqp/get")
    public ResponseEntity<?> getMessage() {
        Object message = amqpTemplate.receiveAndConvert("test_messages");
        if (message != null) {
            logger.info("Message 2 from the queue: {}", message);
            return new ResponseEntity(message, HttpStatus.OK);
        } else {
            logger.info("Empty message queue");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

}
