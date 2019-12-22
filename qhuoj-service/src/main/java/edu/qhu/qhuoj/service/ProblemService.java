package edu.qhu.qhuoj.service;

import edu.qhu.qhuoj.entity.Problem;
import edu.qhu.qhuoj.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProblemService {
    @Autowired
    ProblemRepository problemRepository;

    public List<Problem> addProblems(List<Problem> problems){
        for (Problem problem : problems){
            Problem findProblem = problemRepository.findByName(problem.getName());
            if (null != findProblem){
                problems.remove(problem);
            }
        }
        return problemRepository.saveAll(problems);
    }

    public Problem addProblem(Problem problem){
        Problem findProblem = problemRepository.findByName(problem.getName());
        if (null != findProblem) {
            return null;
        }
        return problemRepository.save(problem);
    }

    public Problem getProblemById(int id){
        Optional<Problem> findProblem = problemRepository.findById(id);
        if (findProblem.isPresent()){
            return findProblem.get();
        }
        return null;
    }

    public List<Problem> getProblemByName(String name){
        return problemRepository.findByNameLike(name);
    }

    public List<Problem> getAllProblems(){
        return problemRepository.findAll();
    }


}
