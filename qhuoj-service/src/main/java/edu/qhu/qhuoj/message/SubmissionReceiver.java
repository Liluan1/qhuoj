package edu.qhu.qhuoj.message;

import com.rabbitmq.client.Channel;
import edu.qhu.qhuoj.entity.Submission;
import edu.qhu.qhuoj.judge.Dispatcher;
import edu.qhu.qhuoj.service.SubmissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RabbitListener(queues = "${message.submission.queue}")
public class SubmissionReceiver {
    @RabbitHandler
    public void handler(Map<String, Object> submissionMessage, Channel channel, Message message){
        String status = (String)submissionMessage.get("status");
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            if ("createSubmission".equals(status)){
                int submissionId = (int)submissionMessage.get("submissionId");
                Submission submission = submissionService.getSubmissionById(submissionId);
                if(null != submission) {
                    dispatcher.createNewTask(submissionId);
                }
                log.warn("invaild submission");
            }
        } catch (IOException e) {
            e.printStackTrace();
            //丢弃这条消息
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
            log.error("receiver fail");
        }
    }


    @Autowired
    SubmissionService submissionService;

    @Autowired
    Dispatcher dispatcher;

    private final Logger log = LoggerFactory.getLogger(SubmissionReceiver.class);

}
