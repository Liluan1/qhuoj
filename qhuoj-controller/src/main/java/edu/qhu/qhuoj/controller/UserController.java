package edu.qhu.qhuoj.controller;

import edu.qhu.qhuoj.entity.ResponseMsg;
import edu.qhu.qhuoj.entity.User;
import edu.qhu.qhuoj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseMsg studentRegister(@RequestBody User user, HttpServletRequest request, HttpServletResponse response){
        User newUser = userService.addStudent(user);
        if (null == newUser){
            response.setStatus(500);
            return ResponseMsg.error("User Already Exists", request.getServletPath());
        }

        return ResponseMsg.success(newUser, request.getServletPath());
    }

    @RequestMapping(value = "all", method = RequestMethod.POST)
    public ResponseMsg allStudentRegister(@RequestBody List<User> users, HttpServletRequest request,
                                          HttpServletResponse response){
        List<User> newUsers = userService.addAllStudent(users);
        if (null == newUsers){
            response.setStatus(500);
            return ResponseMsg.error("User Already Exists", request.getServletPath());
        }
        return ResponseMsg.success(newUsers, request.getServletPath());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseMsg getStudentById(@PathVariable int id, HttpServletRequest request, HttpServletResponse response){
        User findstudent = userService.getUserById(id);
        if (null == findstudent){
            response.setStatus(500);
            return ResponseMsg.error("User Not Found", request.getServletPath());
        }
        return ResponseMsg.success(findstudent, request.getServletPath());
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public ResponseMsg getAllStudents(HttpServletRequest request){
        return ResponseMsg.success(userService.getAllStudent(), request.getServletPath());
    }


}
