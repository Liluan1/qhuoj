package edu.qhu.qhuoj.controller;

import edu.qhu.qhuoj.entity.Problem;
import edu.qhu.qhuoj.entity.ResponseMsg;
import edu.qhu.qhuoj.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController()
@RequestMapping(value = "/problems")
public class ProblemController {
    @Autowired
    ProblemService problemService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseMsg addProblem(@RequestBody Problem problem, HttpServletRequest request, HttpServletResponse response){
        Problem newProblem = problemService.addProblem(problem);
        if (null == newProblem){
            response.setStatus(500);
            return ResponseMsg.error("Problem Already Exist", request.getServletPath());
        }
        return ResponseMsg.success(newProblem, request.getServletPath());
    }

    @RequestMapping(value = "all", method = RequestMethod.POST)
    public ResponseMsg addProblems(@RequestBody List<Problem> problems, HttpServletRequest request, HttpServletResponse response){
        List<Problem> newProblems = problemService.addProblems(problems);
        if (null == newProblems){
            response.setStatus(500);
            return ResponseMsg.error("Problem Already Exist", request.getServletPath());
        }
        return ResponseMsg.success(newProblems, request.getServletPath());
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseMsg getAllProblems(HttpServletRequest request){
        return ResponseMsg.success(problemService.getAllProblems(), request.getServletPath());
    }

    @RequestMapping(value = "/{exp}", method = RequestMethod.GET)
    public ResponseMsg getProblem(@PathVariable("exp") String exp, HttpServletRequest request, HttpServletResponse response){
        boolean result = exp.matches("[0-9]+");
        if(result) {
            Problem findProblem = problemService.getProblemById(Integer.parseInt(exp));
            if (null == findProblem){
                response.setStatus(500);
                return ResponseMsg.error("Problem Not Found", request.getServletPath());
            }
            return ResponseMsg.success(findProblem, request.getServletPath());
        }else {
            List<Problem> findProblems = problemService.getProblemByName(exp);
            if (null == findProblems){
                response.setStatus(500);
                return ResponseMsg.error("Problem Not Found", request.getServletPath());
            }
            return ResponseMsg.success(findProblems, request.getServletPath());
        }

    }

}
