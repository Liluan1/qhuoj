package edu.qhu.qhuoj.judge;

import edu.qhu.qhuoj.entity.Testpoint;
import edu.qhu.qhuoj.entity.Submission;
import edu.qhu.qhuoj.service.MessageService;
import edu.qhu.qhuoj.service.TestpointService;
import edu.qhu.qhuoj.service.SubmissionService;
import edu.qhu.qhuoj.util.DigitalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Dispatcher {
    public void createNewTask(int submissionId){
        synchronized (this){
            String fileDirectory = String.format("%s/qhuoj-%s", new Object[] {workDirectory, submissionId});
            String fileName = DigitalUtils.getRandomString(12, "ALPHA");
            Submission submission = submissionService.getSubmissionById(submissionId);
            preprocess(submission, fileDirectory, fileName);
            if (compile(submission, fileDirectory, fileName)){
                runProgram(submission, fileDirectory, fileName);
            }
        }
    }

    private void preprocess(Submission submission, String fileDirectory, String fileName){
        try {
            int problemId = submission.getProblem().getId();
            preprocessor.createTestCode(submission, fileDirectory, fileName);
            preprocessor.fetchTestPonits(problemId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean compile(Submission submission, String fileDirectory, String fileName){
        int submissionId = submission.getId();
        Map<String, Object> compileResult = compiler.createCompile(submission, fileDirectory, fileName);
        onCompileFinished(submissionId, compileResult);
        return (boolean)compileResult.get("isSuccessful");
    }

    private void updateSubmission(int submissionId, int usedTime, int usedMemory, int score, String judgeResult,
                                  String log){
        Submission submission = new Submission();
        submission.setId(submissionId);
        submission.setUsedTime(usedTime);
        submission.setUsedMemory(usedMemory);
        submission.setJudgeScore(score);
        submission.setJudgeResult(judgeResult);
        submission.setJudgeLog(log);
        submissionService.updateSubmission(submission);
    }

    private void runProgram(Submission submission, String fileDirection, String fileName) {
        List<Map<String, Object>> runningResults = new ArrayList<>();
        int submissionId = submission.getId();
        int problemId = submission.getProblem().getId();
        List<Testpoint> testpoints = testpointService.getTestPointByProblemId(problemId);
        for (Testpoint testpoint : testpoints){
            int testpointId = testpoint.getId();
            int testpointScore = testpoint.getScore();
            String inputFilePath = String.format("%s/%s/input#%s.txt", new Object[]{testPointDirectory,problemId,
                    testpointId});
            String stdOutputFilePath = String.format("%s/%s/output#%s.txt", new Object[]{testPointDirectory, problemId,
                    testpointId});
            String outputFilePath = String.format("%s/output#%s.txt", new Object[]{fileDirection, testpointId});
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(new File(outputFilePath));
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Map<String, Object> runningResult = compareAnswer(runner.getRunningResult(submission, fileDirection, fileName,
                    inputFilePath, outputFilePath), outputFilePath, stdOutputFilePath);
            runningResult.put("score", testpointScore);
            runningResults.add(runningResult);
            onTestPointFinished(submissionId, testpointId, runningResult);
        }
        onAllTestpointFinished(submissionId, runningResults);
    }

    private Map<String, Object> compareAnswer(Map<String, Object> runningResult, String outputFilePath,
                                              String stdOutputFilePath){
        String runningResultAbbr = (String) runningResult.get("runningResultAbbr");
        try {
            if (runningResultAbbr.equals("AC") && !comparator.isOutputTheSame(stdOutputFilePath, outputFilePath)){
                runningResult.put("runningResultAddr", "WA");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return runningResult;
    }

    private void onCompileFinished(int submissionId, Map<String, Object> result){
        boolean isSuccessful = (boolean)result.get("isSuccessful");
        String log = (String)result.get("log");
        if (!isSuccessful){
            updateSubmission(submissionId, 0, 0, 0, "CE", log);
        }
        Map<String, Object> resultMessage = new HashMap<>();
        resultMessage.put("status", "CompileFinished");
        resultMessage.put("submissionId", submissionId);
        resultMessage.put("isSuccessful", isSuccessful);
        resultMessage.put("log", log);
        messageService.sendResultMessage(resultMessage);
//        resultSender.sendMessage(message);
    }

    private void onTestPointFinished(int submissionId, int testPointId, Map<String, Object> runningResult){
        String runningResultAbbr = (String) runningResult.get("runningResultAbbr");
        String runningResultName = resultAbbrMapper.get(runningResultAbbr);
        int usedTime = (Integer) runningResult.get("usedTime");
        int usedMemory = (Integer) runningResult.get("usedMemory");
        int score = (Integer) runningResult.get("score");

        Map<String, Object> resultMessage = new HashMap<>();
        resultMessage.put("status", "TestPointFinished");
        resultMessage.put("submissionId", submissionId);
        resultMessage.put("testPointId", testPointId);
        resultMessage.put("runningResult", runningResultName);
        resultMessage.put("usedTime", usedTime);
        resultMessage.put("usedMemory", usedMemory);
        resultMessage.put("score", score);
        messageService.sendResultMessage(resultMessage);
//        resultSender.sendMessage(resultMessage);
    }

    private void onAllTestpointFinished(int submissionId, List<Map<String, Object>> runningResults){
        int totalTime = 0;
        int maxMemory = 0;
        int totalScore = 0;
        String runningResultAbbr = "AC";
        String log = "System Error";

        for (Map<String, Object> runningResult : runningResults){
            String tmpResultAbbr = (String) runningResult.get("runningResultAbbr");
            int usedTime = (Integer) runningResult.get("usedTime");
            int usedMemory = (Integer) runningResult.get("usedMemory");
            int score = (Integer) runningResult.get("score");

            totalTime += usedTime;

            if (usedMemory > maxMemory){
                maxMemory = usedMemory;
            }

            if ("AC".equals(tmpResultAbbr)) {
                totalScore += score;
            }else{
                runningResultAbbr = tmpResultAbbr;
            }
        }
        log = getJudgeLog(runningResults, runningResultAbbr, totalTime, maxMemory, totalScore);
        String runningResultName = resultAbbrMapper.get(runningResultAbbr);
        submissionService.updateSubmission(new Submission(submissionId, totalTime, maxMemory, runningResultAbbr,
                totalScore, log));

        Map<String, Object> resultMessage = new HashMap<>();
        resultMessage.put("status", "AllTestPointFinished");
        resultMessage.put("submissionId", submissionId);
        resultMessage.put("runningResult", runningResultName);
        resultMessage.put("totalTime", totalTime);
        resultMessage.put("maxMemory", totalTime);
        resultMessage.put("totalScore", totalScore);
        messageService.sendResultMessage(resultMessage);
//        resultSender.sendMessage(resultMessage);

    }

    /**
     * 格式化运行时日志.
     * @param runningResults - 对各个测试点的评测结果集
     * @param runningResultAbbr
     * @param totalTime
     * @param maxMemory
     * @param totalScore
     * @return
     */
    private String getJudgeLog(List<Map<String, Object>> runningResults,
                               String runningResultAbbr, int totalTime, int maxMemory, int totalScore) {
        int checkpointId = -1;
        String runtimeResultName = resultAbbrMapper.get(runningResultAbbr);

        StringBuilder formatedLogBuilder = new StringBuilder();
        formatedLogBuilder.append("Compile Successfully.\n\n");
        for ( Map<String, Object> runningResult : runningResults ) {
            String tmpRunningResultAbbr = (String) runningResult.get("runningResultAbbr");
            String tmpRunningResultName = resultAbbrMapper.get(tmpRunningResultAbbr);
            int usedTime = (Integer) runningResult.get("usedTime");
            int usedMemory = (Integer) runningResult.get("usedMemory");
            int score = (Integer) runningResult.get("score");

            if ( !"AC".equals(tmpRunningResultAbbr) ) {
                score = 0;
            }
            formatedLogBuilder.append(String.format("- Test Point #%d: %s, Time = %d ms, Memory = %d KB, Score = %d\n",
                    new Object[] { ++ checkpointId, tmpRunningResultName, usedTime, usedMemory, score }));
        }
        formatedLogBuilder.append(String.format("\n%s, Time = %d ms, Memory = %d KB, Score = %d\n",
                new Object[] { runtimeResultName, totalTime, maxMemory, totalScore }));
        return formatedLogBuilder.toString();
    }


    @Autowired
    SubmissionService submissionService;

    @Autowired
    Preprocessor preprocessor;

    @Autowired
    Compiler compiler;

    @Autowired
    TestpointService testpointService;

    @Autowired
    Runner runner;

    @Autowired
    Comparator comparator;

    @Autowired
    MessageService messageService;

    @Value("${judge.workDir}")
    String workDirectory;

    @Value("${judge.testPointDir}")
    String testPointDirectory;

    @Value("#{${judge.resultAbbrMapper}}")
    Map<String, String> resultAbbrMapper;
}
