package edu.qhu.qhuoj.controller;

import edu.qhu.qhuoj.entity.TestPoint;
import edu.qhu.qhuoj.judge.Runner;
import edu.qhu.qhuoj.service.MessageService;
import edu.qhu.qhuoj.service.TestPointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class QhuojControllerApplicationTests {
    @Test
    void contextLoads() {
        List<TestPoint> findTestPoint = testpointService.getTestPointByProblemId(1);
        System.out.println(findTestPoint);
    }

    @Test
    void Compilement() {
        Map<String, Object> runtimeResult = compileRunner.getRuntimeResult("gcc -O2 -s -Wall -o /tmp/qhuoj-8/DlZJxSwAbfYw.exe /tmp/qhuoj-8/DlZJxSwAbfYw.c" +
                " -lm", "Liluan", "lijiahui19990316", null, "/tmp/qhuoj-8/DlZJxSwAbfYw-compiler.log", 5000, 0);

    }


    @Autowired
    MessageService messageService;
    @Autowired
    TestPointService testpointService;
    @Autowired
    Runner compileRunner;
}
