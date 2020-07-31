package com.keith.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface StreamClient {

    String INPUT = "myMessage1";

    String OUTPUT = "myMessage";

    String INPUT_RECEIVED = "myMessageReceived1";

    String OUTPUT_RECEIVED = "myMessageReceived";

    @Input(StreamClient.INPUT)
    SubscribableChannel input();

    @Output(StreamClient.OUTPUT)
    MessageChannel output();

    @Input(StreamClient.INPUT_RECEIVED)
    SubscribableChannel inputReceived();

    @Output(StreamClient.OUTPUT_RECEIVED)
    MessageChannel outputReceived();
}
