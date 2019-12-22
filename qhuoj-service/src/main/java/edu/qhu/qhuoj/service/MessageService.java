package edu.qhu.qhuoj.service;

import edu.qhu.qhuoj.message.ResultSender;
import edu.qhu.qhuoj.message.SubmissionSender;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MessageService {

    public void sendSubmissionMessage(Map<String, Object> message){
        submissionSender.sendMessage(submissionQueue, message);
    }

    public void sendResultMessage(Map<String, Object> message){
        submissionSender.sendMessage(resultQueue,message);
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
    SubmissionSender submissionSender;

    @Autowired
    ResultSender resultSender;
}
