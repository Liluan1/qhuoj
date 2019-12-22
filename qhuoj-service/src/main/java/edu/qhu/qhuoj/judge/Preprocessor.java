package edu.qhu.qhuoj.judge;

import edu.qhu.qhuoj.entity.Testpoint;
import edu.qhu.qhuoj.entity.Language;
import edu.qhu.qhuoj.entity.Submission;
import edu.qhu.qhuoj.service.TestpointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class Preprocessor {

    void createTestCode(Submission submission, String workDirectory, String baseFileName) {

        File workDirFile = new File(workDirectory);
        if (!workDirFile.exists() && !workDirFile.mkdirs()) {
            log.error("Failed to create directory: " + workDirectory);
        } else {
            log.info("Create directory" + workDirectory);
        }
        //读写权限
        Language language = submission.getLanguage();
        String code = replaceClassName(language, submission.getCode(), baseFileName);
        //随机文件名
        String codeFilePath = String.format("%s/%s.%s", new Object[]{workDirectory, baseFileName,
                language.getSuffix()});
        try {
            log.info("Create code file" + codeFilePath);
            FileOutputStream outputStream = new FileOutputStream(new File(codeFilePath));
            outputStream.write(code.getBytes());
            outputStream.close();
        } catch (IOException e) {
            log.error("Failed to create code file" + codeFilePath);
            e.printStackTrace();
        }
    }

    /**
     * 替换Java文件的类名
     *
     * @param language
     * @param code
     * @param newClassName
     * @return
     */
    private String replaceClassName(Language language, String code, String newClassName) {
        log.info("Replace class name" + newClassName);
        if (!language.getName().equalsIgnoreCase("Java")) {
            return code;
        }
        return code.replaceAll("class[ \n]+Main", "class " + newClassName);
    }

    public void fetchTestPonits(int problemId) {
        String testPointDirectory = String.format("%s/%s", this.testPointDirectory, problemId);
        log.info("Create test point directory");
        File testPointsFile = new File(testPointDirectory);
        if (!testPointsFile.exists() && !testPointsFile.mkdirs()) {
            log.error("Failed to create testpoint diectory: " + testPointDirectory);
        }
        List<Testpoint> testPoints = testpointService.getTestPointByProblemId(problemId);
        try {
            log.info("Create test point file");
            for (Testpoint testpoint : testPoints) {
                int testPointId = testpoint.getId();
                {
                    String filePath = String.format("%s/input#%s.txt", testPointDirectory, testPointId);
                    FileOutputStream outputStream = new FileOutputStream(new File(filePath));
                    String input = testpoint.getInput();
                    outputStream.write(input.getBytes());
                    outputStream.close();
                }
                {
                    String filePath = String.format("%s/output#%s.txt", testPointDirectory, testPointId);
                    FileOutputStream outputStream = new FileOutputStream(new File(filePath));
                    String output = testpoint.getOutput();
                    outputStream.write(output.getBytes());
                    outputStream.close();
                }

            }
        } catch (IOException e) {
            log.error("Failed to create test point file");
            e.printStackTrace();
        }
    }

    @Value("${judge.testPointDir}")
    String testPointDirectory;

    @Autowired
    TestpointService testpointService;

    private final Logger log = LoggerFactory.getLogger(Preprocessor.class);

}
