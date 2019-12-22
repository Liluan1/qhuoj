package edu.qhu.qhuoj.judge;

import edu.qhu.qhuoj.entity.Language;
import edu.qhu.qhuoj.entity.Submission;
import edu.qhu.qhuoj.util.NativeLibraryLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Runner {

    public Map<String, Object> getRunningResult(Submission submission, String fileDirector, String fileName,
                                                String inputFilePath, String outputFilePath){
        log.info("Start running");
        String abbr = null;
        String commandLine = getCommandLine(submission, fileDirector, fileName);
        int timeLimit = getTimeLimit(submission);
        int memoryLimit = getMemoryLimit(submission);
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> runningResult = getRuntimeResult(commandLine, systemUsername, systemPassword, inputFilePath,
                outputFilePath, timeLimit, memoryLimit);
        int exitCode = (Integer) runningResult.get("exitCode");
        int usedTime = (Integer) runningResult.get("usedTime");
        int usedMemory = (Integer) runningResult.get("usedMemory");
        if(exitCode == 0){
            abbr = "AC";
        }else if(usedTime > timeLimit){
            abbr = "TLE";
        }else if (usedMemory > memoryLimit){
            abbr = "MLE";
        }else{
            abbr = "RE";
        }
        result.put("runningResultAbbr", abbr);
        result.put("usedTime", usedMemory);
        result.put("usedMemory", usedMemory);
        return result;
    }



    private String getCommandLine(Submission submission, String fileDirector, String fileName){
        log.info("Get running command");
        Language language = submission.getLanguage();
        String filePathName = String.format("%s/%s", new Object[]{fileDirector, fileName});
        StringBuffer runCommand = new StringBuffer(language.getRunningCommand().replaceAll("\\{filename\\}",
                filePathName));
        if (language.getName().equalsIgnoreCase("Java")){
            int index = runCommand.lastIndexOf("/");
            runCommand.setCharAt(index, ' ');
        }
        return runCommand.toString();
    }

    private int getTimeLimit(Submission submission){
        log.info("Get running time limit");
        Language language = submission.getLanguage();
        int timeLimit = submission.getProblem().getTimeLimit();

        if(language.getName().equalsIgnoreCase("Java")){
            timeLimit *= 2;
        }
        return timeLimit;
    }

    private int getMemoryLimit(Submission submission){
        log.info("Get running memory limit");
        int memoryLimit = submission.getProblem().getMemoryLimit();
        return memoryLimit;
    }

    public native Map<String, Object> getRuntimeResult(String commandLine,
                                                       String systemUsername, String systemPassword, String inputFilePath,
                                                       String outputFilePath, int timeLimit, int memoryLimit);


    @Autowired
    Runner compilerRunner;

    @Value("${system.username}")
    private String systemUsername;

    @Value("${system.password}")
    private String systemPassword;

    private final Logger log = LoggerFactory.getLogger(Runner.class);

    static {
        try {
            NativeLibraryLoader.loadLibrary("JudgerCore");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
