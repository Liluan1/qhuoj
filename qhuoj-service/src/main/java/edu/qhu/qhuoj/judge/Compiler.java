package edu.qhu.qhuoj.judge;

import edu.qhu.qhuoj.entity.Submission;
import edu.qhu.qhuoj.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class Compiler {

    public Map<String, Object> createCompile(Submission submission, String fileDirectory, String fileName){
        String command = getCompileCommandLine(submission, fileDirectory, fileName);
        String compileLogPath = getCompileLogPath(fileDirectory, fileName);
        return getCompileResult(command, compileLogPath);
    }

    private String getCompileCommandLine(Submission submission, String fileDirectory, String fileName){
        String filePathName =String.format("%s/%s", new Object[]{fileDirectory, fileName});
        String compileCommand = submission.getLanguage().getCompileCommand().replaceAll("\\{filename\\}", filePathName);
        return compileCommand;
    }

    private String getCompileLogPath(String fileDirectory, String fileName){
        return String.format("%s/%s-compiler.log", new Object[]{fileDirectory, fileName});
    }

    private Map<String, Object> getCompileResult(String commandLine, String compileLogPath){
        String inputFilePath = null;
        int timeLimit = 5000;
        int memoryLimit = 0;

        Map<String, Object> runningResult = compilerRunner.getRuntimeResult(commandLine, systemUsername, systemPassword,
                inputFilePath, compileLogPath,
                timeLimit, memoryLimit);
        Map<String, Object> result = new HashMap<>(3, 1);

        boolean isSuccessful = false;
        if ( runningResult != null ) {
            int exitCode = (Integer)runningResult.get("exitCode");
            isSuccessful = exitCode == 0;
        }
        result.put("isSuccessful", isSuccessful);
        result.put("log", getCompileLog(compileLogPath));
        return result;
    }

    private String getCompileLog(String compileLogPath){
        FileInputStream inputStream = null;
        String compileLog = null;
        try {
            inputStream = new FileInputStream(compileLogPath);
            compileLog = IOUtils.toString(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return compileLog;
    }

    @Autowired
    Runner compilerRunner;

    @Value("${system.username}")
    private String systemUsername;

    @Value("${system.password}")
    private String systemPassword;

}
