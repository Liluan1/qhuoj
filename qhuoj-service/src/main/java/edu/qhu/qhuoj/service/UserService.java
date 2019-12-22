package edu.qhu.qhuoj.service;

import edu.qhu.qhuoj.entity.User;
import edu.qhu.qhuoj.repository.UserRepository;
import edu.qhu.qhuoj.util.EncryptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User addStudent(User user){
        User findUser = userRepository.findByUsername(user.getUsername());
        if (null != findUser){
            return null;
        }
        String password = user.getPassword();
        String encodedPassword = EncryptionUtils.md5Encode(password);
        user.setPassword(encodedPassword);
        user.setScore(0);
        User addUser = userRepository.save(user);
        addUser.setPassword("*********");
        return addUser;
    }

    public List<User> addAllStudent(List<User> users){
        for (User user : users){
            User findUser = userRepository.findByUsername(user.getUsername());
            if (null != findUser){
                users.remove(user);
                continue;
            }
            String password = user.getPassword();
            String encodedPassword = EncryptionUtils.md5Encode(password);
            user.setPassword(encodedPassword);
        }
        return userRepository.saveAll(users);
    }

    public User getUserById(int id){
        Optional<User> opStudent = userRepository.findById(id);
        if(opStudent.isPresent()) {
            User findstudent = opStudent.get();
            findstudent.setPassword("*********");
            return findstudent;
        }
        return null;
    }

    public List<User> getAllStudent(){
        List<User> findstudents = userRepository.findAll();
        for (User user : findstudents){
            user.setPassword("*********");
        }
        return findstudents;
    }


}
