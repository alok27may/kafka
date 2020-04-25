package com.kafka.java.controller;

import com.kafka.java.consumer.MyTopicConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KafkaController {

    private KafkaTemplate<String, String> template;
    private MyTopicConsumer myTopicConsumer;
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaController.class);

    public KafkaController(KafkaTemplate<String, String> template, MyTopicConsumer myTopicConsumer) {
        this.template = template;
        this.myTopicConsumer = myTopicConsumer;
    }

    @GetMapping("/")
    public String ping() {
        return "pong";
    }


    @GetMapping("/kafka/produce")
    public void produce(@RequestParam String message) {
        template.send("myTopic", message);
    }

    @GetMapping("/kafka/produce/callback")
    public void produceWithCallback(@RequestParam String message) {
        final ListenableFuture<SendResult<String, String>> listenableFuture = template.send("myTopic", message);
        callCallback(listenableFuture, message);
    }

    private void callCallback(final ListenableFuture<SendResult<String, String>> listenableFuture, final String message) {
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(final Throwable throwable) {
                LOGGER.error("Unable to send message {}", throwable.getMessage(), throwable);
            }

            @Override
            public void onSuccess(final SendResult<String, String> stringStringSendResult) {
                LOGGER.info("Send message = {} with Offset = {}", message ,stringStringSendResult.getRecordMetadata().offset());
            }
        });
    }

    @GetMapping("/kafka/messages")
    public List<String> getMessages() {
        return myTopicConsumer.getMessages();
    }

}
