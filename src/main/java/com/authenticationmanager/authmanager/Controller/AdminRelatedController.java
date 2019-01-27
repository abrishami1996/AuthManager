package com.authenticationmanager.authmanager.Controller;
import com.authenticationmanager.authmanager.Model.*;
import com.authenticationmanager.authmanager.repository.FacultyEmployeeRepository;
import com.authenticationmanager.authmanager.repository.ProfessorRepository;
import com.authenticationmanager.authmanager.repository.StudentRepository;
import com.authenticationmanager.authmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RestController
public class AdminRelatedController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ProfessorRepository professorRepository;
    @Autowired
    FacultyEmployeeRepository facultyEmployeeRepository;

    @RequestMapping(value = "/DeleteUser", method = RequestMethod.POST)
    public String DeleteUser(@RequestParam String username) {

        if (userRepository.existsByUsername(username)) {
            User u = userRepository.findByUsername(username);
            int key=u.getFK();
            if(u.getTypeOfUser()=="Student"){
                studentRepository.deleteById(key);
            }
            else if(u.getTypeOfUser()=="Professor"){
                professorRepository.deleteById(key);
            }
            else if(u.getTypeOfUser()=="Employee"){
                facultyEmployeeRepository.deleteById(key);
            }

            userRepository.deleteByUsername(username);

            return "ok";

        } else {
            return "کاربری با این شماره وجود ندارد";
        }
    }

    @RequestMapping(value = "/getProfessors" , method = RequestMethod.GET)
    public List<Professor> getProfessors()
    {
        return professorRepository.findAll();
    }

    @RequestMapping(value = "/getStudents" , method = RequestMethod.GET)
    public List<Student> getStudents()
    {
        return studentRepository.findAll();
    }

    @RequestMapping(value = "/getEmployees" , method = RequestMethod.GET)
    public List<FacultyEmployee> getEmployees()
    {
        return facultyEmployeeRepository.findAll();
    }
}
