package edu.qhu.qhuoj.service;

import edu.qhu.qhuoj.entity.Submission;
import edu.qhu.qhuoj.entity.User;
import edu.qhu.qhuoj.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubmissionService {
    public Submission getSubmissionById(int id){
        boolean isPresent = submissionRepository.findById(id).isPresent();
        if (isPresent){
            Submission findSubmission = submissionRepository.findById(id).get();
            return findSubmission;
        }
        return null;
    }

    public List<Submission> getSubmissionByUserId(int id){
        List<Submission> findSubmissions = submissionRepository.findByUser_Id(id);
        if(null != findSubmissions){
            for (Submission submission : findSubmissions){
                User user = submission.getUser();
                user.setPassword("*********");
                submission.setUser(user);
            }
        }
        return findSubmissions;
    }

    public Submission addSubmission(Submission submission){
        Map<String, Object> message = new HashMap<>();
        Submission newSubmission = submissionRepository.save(submission);
        message.put("status", "createSubmission");
        message.put("submissionId", newSubmission.getId());
        messageService.sendSubmissionMessage(message);
        return newSubmission;
    }

    public List<Submission> getAllSubmission(){
        return submissionRepository.findAll();
    }

    public Submission updateSubmission(Submission submission){
        int submissionId = submission.getId();
        boolean isPresent = submissionRepository.findById(submissionId).isPresent();
        if (!isPresent){
            // 增加自定义的Exception
        }else{
            Submission findSubmission = submissionRepository.findById(submissionId).get();
            findSubmission.setUsedTime(submission.getUsedTime());
            findSubmission.setUsedMemory(submission.getUsedMemory());
            findSubmission.setJudgeResult(submission.getJudgeResult());
            findSubmission.setJudgeScore(submission.getJudgeScore());
            findSubmission.setJudgeLog(submission.getJudgeLog());
            submissionRepository.save(findSubmission);
        }
        return null;
    }

    @Autowired
    SubmissionRepository submissionRepository;
    @Autowired
    MessageService messageService;
    @Autowired
    ProblemService problemService;
    @Autowired
    UserService userService;
    @Autowired
    LanguageService languageService;
}
