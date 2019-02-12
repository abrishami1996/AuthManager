package com.microservice.educationPortal.authmanager.Controller;
import com.microservice.educationPortal.authmanager.Model.FacultyEmployee;
import com.microservice.educationPortal.authmanager.Model.Professor;
import com.microservice.educationPortal.authmanager.Model.Student;
import com.microservice.educationPortal.authmanager.Model.User;
import com.microservice.educationPortal.authmanager.repository.FacultyEmployeeRepository;
import com.microservice.educationPortal.authmanager.repository.ProfessorRepository;
import com.microservice.educationPortal.authmanager.repository.StudentRepository;
import com.microservice.educationPortal.authmanager.repository.UserRepository;
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
import java.util.Optional;

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
    public String GetRole(HttpServletRequest request) {
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

    @GetMapping("/getUserDetails")
    public String GetUserDetails(HttpServletRequest request)
    {
        String username=request.getUserPrincipal().getName();
        User user=userRepository.findByUsername(username);
        int fk=user.getFK();
        if(request.isUserInRole("ROLE_STUDENT"))
        {
            Student student = studentRepository.findById(fk);
            return (student.getFirstName()+" "+student.getLastName());
        }
        else if(request.isUserInRole("ROLE_EMPLOYEE"))
        {
            FacultyEmployee facultyEmployee = facultyEmployeeRepository.findById(fk);
            return (facultyEmployee.getFirstName()+" "+facultyEmployee.getLastName());
        }
        else if(request.isUserInRole("ROLE_PROFESSOR"))
        {
            Professor professor = professorRepository.findById(fk);
            return (professor.getFirstName()+" "+professor.getLastName());
        }
        else return username;
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/getProfessors" , method = RequestMethod.GET)
    public List<Professor> getProfessors()
    {
        return professorRepository.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/getStudents" , method = RequestMethod.GET)
    public List<Student> getStudents()
    {
        return studentRepository.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/getEmployees" , method = RequestMethod.GET)
    public List<FacultyEmployee> getEmployees()
    {
        return facultyEmployeeRepository.findAll();
    }

    @GetMapping("/getUserId")
    public String getuserID(HttpServletRequest request) {
        String username=request.getUserPrincipal().getName();
        User user=userRepository.findByUsername(username);
        return user.getId().toString();
    }


}
