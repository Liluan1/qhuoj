package edu.qhu.qhuoj.message;

import edu.qhu.qhuoj.entity.ResponseMsg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class SubmissionListener {

    @EventListener
    public void submissionEventHandler(SubmissionEvent event) throws IOException {
        int submissionId = event.getSubmissionId();
        String judgeResult = event.getJudgeResult();
        String message = event.getMessage();
        boolean isCompleted = event.isCompleted();
        SseEmitter sseEmitter = sseEmitters.get(submissionId);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("judgeResult", judgeResult);
        resultMap.put("message", message);
        ResponseMsg responseMsg = ResponseMsg.success(resultMap, realTimeAccessPath);

        if (null != sseEmitter) {
            sseEmitter.send(responseMsg);

            if (isCompleted) {
                sseEmitter.complete();
                removeSseEmitters(submissionId);
            }
        }
    }

    public void addSseEmitters(int submissionId, SseEmitter sseEmitter){
        sseEmitters.put(submissionId, sseEmitter);
    }

    public void removeSseEmitters(int submissionId){
        sseEmitters.remove(submissionId);
    }

    private static Map<Integer, SseEmitter> sseEmitters = new HashMap<>();

    @Value("${message.realTime.accessPath}")
    private String realTimeAccessPath;
}
