package edu.qhu.qhuoj.message;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SubmissionSender implements RabbitTemplate.ReturnCallback {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendMessage(String routingKey, Map<String, Object> message){
        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                assert correlationData != null;
                System.out.println("SubmissionSender 消息发送失败" + cause + correlationData.toString());
            } else {
                System.out.println("SubmissionSender 消息发送成功 ");
            }
        });
        rabbitTemplate.convertAndSend(routingKey, message);
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("sender return success" + message.toString()+"==="+i+"==="+s1+"==="+s2);
    }
}
