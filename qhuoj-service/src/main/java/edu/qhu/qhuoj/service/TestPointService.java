package edu.qhu.qhuoj.service;

import edu.qhu.qhuoj.entity.Problem;
import edu.qhu.qhuoj.entity.TestPoint;
import edu.qhu.qhuoj.repository.ProblemRepository;
import edu.qhu.qhuoj.repository.TestPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestPointService {
    public List<TestPoint> getTestPointByProblemId(int problemId){
        List<TestPoint> findTestPoints = testpointRepository.findByProblem_Id(problemId);
        return findTestPoints;
    }

    public TestPoint getTestPointById(int testPointId){
        boolean isPresent = testpointRepository.findById(testPointId).isPresent();
        TestPoint findTestPoint = null;
        if(isPresent){
            findTestPoint = testpointRepository.findById(testPointId).get();
        }
        return findTestPoint;
    }

    public TestPoint addTestPoint(TestPoint testpoint){
        TestPoint saveTestPoint = testpointRepository.save(testpoint);
        int problemId = testpoint.getProblem().getId();
        Problem findProblem = problemService.getProblemById(problemId);
        int totalScore = findProblem.getTotalScore();
        totalScore += testpoint.getScore();
        findProblem.setTotalScore(totalScore);
        problemRepository.save(findProblem);
        return saveTestPoint;
    }

    @Autowired
    TestPointRepository testpointRepository;

    @Autowired
    ProblemRepository problemRepository;
    
    @Autowired
    ProblemService problemService;
}
