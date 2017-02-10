package com.appb.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Davor Sauer
 */
@Component
public class MessageReceiver {

    private static final Logger logger = LoggerFactory.getLogger(MessageReceiver.class);

    public void receiveMessage(String message) {
        logger.info("Receiving message: {}", message);
    }
}
