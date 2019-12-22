package edu.qhu.qhuoj.controller;

import com.alibaba.fastjson.JSONObject;
import edu.qhu.qhuoj.entity.*;
import edu.qhu.qhuoj.message.SubmissionListener;
import edu.qhu.qhuoj.service.LanguageService;
import edu.qhu.qhuoj.service.ProblemService;
import edu.qhu.qhuoj.service.SubmissionService;
import edu.qhu.qhuoj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/submissions")
public class SubmissionController {
    @RequestMapping(method = RequestMethod.POST)
    public ResponseMsg addSubmission(@RequestBody JSONObject jsonObject, HttpServletRequest request){
        Problem findProblem = problemService.getProblemById(jsonObject.getIntValue("problemId"));
        Language findLanguage = languageService.getLanguageById(jsonObject.getIntValue("languageId"));
        User findUser = userService.getUserById(jsonObject.getIntValue("userId"));
        Submission submission = new Submission(findProblem, findUser, findLanguage,
                jsonObject.getString("code"));
        Submission newSubmission = submissionService.addSubmission(submission);
        return ResponseMsg.success(newSubmission, request.getServletPath());
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public ResponseMsg getAllSubmission(HttpServletRequest request){
        List<Submission> allSubmission = submissionService.getAllSubmission();
        return ResponseMsg.success(allSubmission, request.getServletPath());
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseMsg getSubmissionById(@PathVariable("id") int submissionId, HttpServletRequest request){
        Submission findSubmission = submissionService.getSubmissionById(submissionId);
        return ResponseMsg.success(findSubmission, request.getServletPath());
    }

    @RequestMapping(value = "realtime/{id}", method = RequestMethod.GET)
    public Object getRealTimeSubmission(@PathVariable int id, HttpServletRequest request){
        Submission findSubmission = submissionService.getSubmissionById(id);
        if (null != findSubmission.getJudgeResult()){
            return ResponseMsg.success(findSubmission, request.getServletPath());
        }
        SseEmitter sseEmitter = new SseEmitter(0L);
        submissionListener.addSseEmitters(id, sseEmitter);
        return sseEmitter;
    }


    @Autowired
    SubmissionService submissionService;

    @Autowired
    SubmissionListener submissionListener;
    @Autowired
    ProblemService problemService;
    @Autowired
    LanguageService languageService;
    @Autowired
    UserService userService;
}
