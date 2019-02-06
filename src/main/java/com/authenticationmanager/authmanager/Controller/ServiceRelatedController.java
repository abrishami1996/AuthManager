package com.authenticationmanager.authmanager.Controller;

import com.authenticationmanager.authmanager.Model.FacultyEmployee;
import com.authenticationmanager.authmanager.Model.Professor;
import com.authenticationmanager.authmanager.Model.Student;
import com.authenticationmanager.authmanager.Model.User;
import com.authenticationmanager.authmanager.repository.FacultyEmployeeRepository;
import com.authenticationmanager.authmanager.repository.ProfessorRepository;
import com.authenticationmanager.authmanager.repository.StudentRepository;
import com.authenticationmanager.authmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ServiceRelatedController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ProfessorRepository professorRepository;
    @Autowired
    FacultyEmployeeRepository facultyEmployeeRepository;

    @GetMapping("/getRole")
    public String isStudent(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        } else if (request.isUserInRole("ROLE_STUDENT")) {
            return SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        } else if (request.isUserInRole("ROLE_PROFESSOR")) {
            return SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        } else if (request.isUserInRole("ROLE_EMPLOYEE")) {
            return SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        }

        return HttpStatus.FORBIDDEN.toString();
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

    @GetMapping("/getId")
    public String getuserID(HttpServletRequest request) {
        String username=request.getUserPrincipal().getName();
        User user=userRepository.findByUsername(username);
        return user.getId().toString();
    }


}
