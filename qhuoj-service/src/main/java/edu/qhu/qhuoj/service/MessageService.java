package edu.qhu.qhuoj.service;

import edu.qhu.qhuoj.message.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MessageService {

    public void sendSubmissionMessage(Map<String, Object> message){
        logger.info("Send submission message");
        messageSender.sendMessage(submissionQueue, message);
    }

    public void sendResultMessage(Map<String, Object> message){
        logger.info("Send result message");
        messageSender.sendMessage(resultQueue,message);
    }

    @Value("${message.submission.queue}")
    String submissionQueue;

    @Value("${message.result.queue}")
    String resultQueue;

//    @Bean
//    public Queue submissionQueue(){
//        return new Queue(submissionQueue);
//    }
//
//    @Bean
//    public Queue resultQueue(){
//        return new Queue(resultQueue);
//    }

    @Autowired
    MessageSender messageSender;

    private final Logger logger = LoggerFactory.getLogger(MessageService.class);

}
