package edu.qhu.qhuoj.controller;

import com.alibaba.fastjson.JSONObject;
import edu.qhu.qhuoj.entity.ResponseMsg;
import edu.qhu.qhuoj.entity.TestPoint;
import edu.qhu.qhuoj.service.TestPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/testpoints")
public class TestPointController {
    @RequestMapping(method = RequestMethod.POST)
    public ResponseMsg addTesPoint(@RequestBody TestPoint testPoint, HttpServletRequest request){
        TestPoint addTestPoint = testPointService.addTestPoint(testPoint);
        return ResponseMsg.success(addTestPoint, request.getServletPath());
    }

    @RequestMapping(value = "{testPointId}", method = RequestMethod.GET)
    public ResponseMsg getTesPointById(@PathVariable int testPointId, HttpServletRequest request){
        TestPoint findTestPoint = testPointService.getTestPointById(testPointId);
        if (null != findTestPoint){
            ResponseMsg.success(findTestPoint, request.getServletPath());
        }
        return ResponseMsg.error("TestPoint Not Found", request.getServletPath());
    }

    @RequestMapping(value = "/problem/{problemId}", method = RequestMethod.GET)
    public ResponseMsg getTesPointByProblemId(@PathVariable int problemId, HttpServletRequest request){
        List<TestPoint> findTestPoints = testPointService.getTestPointByProblemId(problemId);
        if (null != findTestPoints){
            ResponseMsg.success(findTestPoints, request.getServletPath());
        }
        return ResponseMsg.error("TestPoint Not Found", request.getServletPath());
    }

    @Autowired
    TestPointService testPointService;
}
