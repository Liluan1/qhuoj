package edu.qhu.qhuoj.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
@Scope("prototype")
public class MessageSender implements RabbitTemplate.ReturnCallback {

    public void sendMessage(String routingKey, Map<String, Object> message){

        RabbitTemplate().setReturnCallback(this);
        RabbitTemplate().setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                assert correlationData != null;
                log.error("Failed to send message" + cause + correlationData.toString());
            } else {
                log.info("Send message success");
            }
        });
        RabbitTemplate().convertAndSend(routingKey, message);

    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        log.info("Sender return success" + message.toString()+"==="+i+"==="+s1+"==="+s2);
    }

//    @Autowired
//    RabbitTemplate rabbitTemplate;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate RabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        return rabbitTemplate;
    }


    @Resource
    private ConnectionFactory connectionFactory;

    private final Logger log = LoggerFactory.getLogger(MessageSender.class);
}
