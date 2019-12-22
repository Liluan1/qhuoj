package edu.qhu.qhuoj.judge;

import edu.qhu.qhuoj.entity.Testpoint;
import edu.qhu.qhuoj.entity.Language;
import edu.qhu.qhuoj.entity.Submission;
import edu.qhu.qhuoj.service.TestpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class Preprocessor {

    void createTestCode(Submission submission, String workDirectory, String baseFileName) throws IOException {
        File workDirFile = new File(workDirectory);
        if (!workDirFile.exists() && !workDirFile.mkdirs() ){
            System.out.println("Failed to create directory: "+workDirectory);
        }
        //读写权限
        Language language = submission.getLanguage();
        String code = replaceClassName(language, submission.getCode(), baseFileName);
        //随机文件名
        String codeFilePath = String.format("%s/%s.%s", new Object[]{workDirectory, baseFileName,
                language.getSuffix()});
        FileOutputStream outputStream = new FileOutputStream(new File(codeFilePath));
        outputStream.write(code.getBytes());
        outputStream.close();
    }

    /**
     * 替换Java文件的类名
     * @param language
     * @param code
     * @param newClassName
     * @return
     */
    private String replaceClassName(Language language, String code, String newClassName){
        if ( !language.getName().equalsIgnoreCase("Java") ) {
            return code;
        }
        return code.replaceAll("class[ \n]+Main", "class " + newClassName);
    }

    public void fetchTestPonits(int problemId) throws IOException {
        String checkPointsFilePath = String.format("%s/%s", testPointDirectory, problemId);
        File checkPointsFile = new File(checkPointsFilePath);
        if (!checkPointsFile.exists() && !checkPointsFile.mkdirs()){
            System.out.println("Failed to create checkpoints diectory: "+checkPointsFilePath);
        }
        List<Testpoint> testpoints = testpointService.getTestPointByProblemId(problemId);
        for (Testpoint testpoint : testpoints){
            int checkpointId = testpoint.getId();
            {
                String filePath = String.format("%s/input#%s.txt", checkPointsFilePath, checkpointId);
                FileOutputStream outputStream = new FileOutputStream(new File(filePath));
                String input = testpoint.getInput();
                outputStream.write(input.getBytes());
                outputStream.close();
            }
            {
                String filePath = String.format("%s/output#%s.txt", checkPointsFilePath, checkpointId);
                FileOutputStream outputStream = new FileOutputStream(new File(filePath));
                String output = testpoint.getOutput();
                outputStream.write(output.getBytes());
                outputStream.close();
            }
        }
    }

    @Value("${judge.testPointDir}")
    String testPointDirectory;

    @Autowired
    TestpointService testpointService;
}
