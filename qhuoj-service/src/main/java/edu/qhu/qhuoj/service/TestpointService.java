package edu.qhu.qhuoj.service;

import edu.qhu.qhuoj.entity.Testpoint;
import edu.qhu.qhuoj.repository.TestpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestpointService {
    public List<Testpoint> getTestPointByProblemId(int problemId){
        return testpointRepository.findByProblem_Id(problemId);
    }

    @Autowired
    TestpointRepository testpointRepository;
}
