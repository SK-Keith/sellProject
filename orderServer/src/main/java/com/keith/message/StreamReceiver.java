package com.keith.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamReceiver {

//    @StreamListener(StreamClient.OUTPUT)
//    public void process(Object message) {
//        log.info("StreamReceiver: {}", message);
//    }


    @StreamListener(value = StreamClient.OUTPUT)
    @SendTo(StreamClient.OUTPUT_RECEIVED)
    public String processDTO(Object message) {
        log.info("StreamReceiver: {}", message);
        return "receive !";
    }

    @StreamListener(StreamClient.OUTPUT_RECEIVED)
    public void processReceived(Object message) {
        log.info("StreamReceiver: {}", message);
    }
}
