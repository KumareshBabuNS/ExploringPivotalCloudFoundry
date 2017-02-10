package com.appb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Davor Sauer
 */
@Controller
public class JmsMessageController {

    private static final Logger logger = LoggerFactory.getLogger(JmsMessageController.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @RequestMapping("jms/send")
    @ResponseBody
    public UUID sendMessage() {
        UUID uuid = UUID.randomUUID();
        logger.info("Sending message 2: {}", uuid);
        jmsTemplate.convertAndSend("test_jms_messages", LocalDateTime.now().toString() + "__" + uuid);

        return uuid;
    }

    @RequestMapping(value = "jms/get")
    public ResponseEntity<?> getMessage() {
        Object message = jmsTemplate.receiveAndConvert("test_jms_messages");
        if (message != null) {
            logger.info("Message 2 from the queue: {}", message);
            return new ResponseEntity(message, HttpStatus.OK);
        } else {
            logger.info("Empty message queue");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

}
