package edu.qhu.qhuoj.message;

import com.rabbitmq.client.Channel;
import edu.qhu.qhuoj.judge.Dispatcher;
import edu.qhu.qhuoj.service.SubmissionService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class ResultReceive {
    @RabbitListener(queues = "${message.result.queue}")
    public void handler(Map<String, Object> resultMessage, Channel channel, Message message) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String status = (String) resultMessage.get("status");
        if ("Error".equals(status)) {
            //Error
            errorHandler(resultMessage);
        } else if ("CompileFinished".equals(status)) {
            //Compile
            compilerFinishedHandler(resultMessage);
        } else if ("TestPointFinished".equals(status)) {
            //TestPoint
            testPointFinishedHandler(resultMessage);
        } else if ("AllTestPointFinished".equals(status)) {
            //AllTestPoint
            allTestPointFinishedHandler(resultMessage);
        } else {
            //Others
            System.out.println("Invalid result message, message = " + resultMessage);
        }
    }

    private void errorHandler(Map<String, Object> resultMessage) {
        int submissionId = (Integer) resultMessage.get("submissionId");
        eventPublisher.publishEvent(new SubmissionEvent(this, submissionId, "System Error", "System Error", true));
    }

    private void compilerFinishedHandler(Map<String, Object> resultMessage) {
        Integer submissionId = (Integer) resultMessage.get("submissionId");
        boolean isSuccessful = (boolean) resultMessage.get("isSuccessful");
        String log = (String) resultMessage.get("log");

        if (isSuccessful) {
            eventPublisher.publishEvent(new SubmissionEvent(this, submissionId, "Running", "Compile Successful", false));
        }else{
            eventPublisher.publishEvent(new SubmissionEvent(this, submissionId, "compiler Error", log, true));
        }
    }

    private void testPointFinishedHandler(Map<String, Object> resultMessage) {
        int submissionId = (Integer) resultMessage.get("submissionId");
        int testPointId = (Integer) resultMessage.get("testPointId");
        String runningResult = (String) resultMessage.get("runningResult");
        int usedTime = (Integer) resultMessage.get("usedTime");
        int usedMemory = (Integer) resultMessage.get("usedMemory");
        int score = (Integer) resultMessage.get("score");

        String message = String.format("- Test Point #%d: %s, Time = %d ms, Memory = %d KB, Score = %d\n",
                new Object[]{testPointId, runningResult, usedTime, usedMemory, score});
        eventPublisher.publishEvent(new SubmissionEvent(this, submissionId,"testPointFinished", message, false));

    }

    private void allTestPointFinishedHandler(Map<String, Object> resultMessage) {
        int submissionId = (Integer) resultMessage.get("submissionId");
        String runningResult = (String) resultMessage.get("runningResult");
        int totalTime = (Integer) resultMessage.get("totalTime");
        int maxMemory = (Integer) resultMessage.get("maxMemory");
        int totalScore = (Integer) resultMessage.get("totalScore");

        String message = String.format("%s, Time = %d ms, Memory = %d KB, Score = %d",
                new Object[]{runningResult, totalTime, maxMemory, totalScore});
        eventPublisher.publishEvent(new SubmissionEvent(this, submissionId,"allTestPointFinished", message, true));

    }


    @Autowired
    SubmissionService submissionService;

    @Autowired
    ApplicationEventPublisher eventPublisher;
}
