package edu.qhu.qhuoj.controller;

import edu.qhu.qhuoj.entity.ResponseMsg;
import edu.qhu.qhuoj.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/test")
public class testController {

    @GetMapping("messages")
    public ResponseMsg sendMessage(HttpServletRequest request,
                                   HttpServletResponse response) throws IOException {
//        messageService.sendMessage("hello world");
        return ResponseMsg.success("success", request.getServletPath());
    }

    @GetMapping("reaaltime")
    public SseEmitter realtime(){
        final SseEmitter emitter = new SseEmitter(0L); //timeout设置为0表示不超时
        mvcTaskExecutor.execute(() -> {
            try {
                for(int i=0;i<100;i++){
                    emitter.send("hello"+i);
                    Thread.sleep(1000*1);
                }
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }


    @GetMapping
    public String test(){
        return "Hello world!";
    }


    @Autowired
    MessageService messageService;

    @Autowired
    ThreadPoolTaskExecutor mvcTaskExecutor;
}
