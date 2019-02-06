package com.authenticationmanager.authmanager.Controller;

import com.authenticationmanager.authmanager.Model.*;
import com.authenticationmanager.authmanager.SignUpRequestClasses.EmployeeRequest;
import com.authenticationmanager.authmanager.SignUpRequestClasses.ProfessorRequest;
import com.authenticationmanager.authmanager.SignUpRequestClasses.StudentRequest;
import com.authenticationmanager.authmanager.repository.RoleRepository;
import com.authenticationmanager.authmanager.repository.FacultyEmployeeRepository;
import com.authenticationmanager.authmanager.repository.ProfessorRepository;
import com.authenticationmanager.authmanager.repository.StudentRepository;
import com.authenticationmanager.authmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.util.WebUtils
import java.util.Collections;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@RestController
public class SignUpController {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    FacultyEmployeeRepository facultyEmployeeRepository;

    @Autowired
    RoleRepository roleRepository;

    private AuthenticationManager authenticationManager;

//________________________________________________________________________________________________________________________________

    // Registering new student

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @CrossOrigin(origins = "http://localhost:63343")
    @RequestMapping(value = "/StudentSignUp", method = RequestMethod.POST)
    public String StudentSignUp(@RequestBody StudentRequest req) {
        if (userRepository.existsByUsername(Long.toString(req.getStudentNumber()))) {
            return ("Fail");
        } else {
            User user = new User();
            user.setTypeOfUser("Student");
            Student student = new Student(req.getStudentNumber(), req.getFirstName(), req.getLastName(), req.getMajor(), req.getEntranceYear(), "عادی");
            Role userRole = roleRepository.findByName("ROLE_STUDENT");
            user.setRoles(Collections.singleton(userRole));
            user.setUsername(Long.toString(student.getStudentNumber()));
            user.setPassword(passwordEncoder.encode(req.getPassword()));
            studentRepository.save(student);
            user.setFK(student.getId());
            userRepository.save(user);
            return ("success");
        }


    }
//________________________________________________________________________________________________________________________________

    // Registering new professor

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @CrossOrigin(origins = "http://localhost:63343")
    @RequestMapping(value = "/ProfessorSignUp", method = RequestMethod.POST)
    public String ProfessorSignUp(@RequestBody ProfessorRequest req) {
        if (userRepository.existsByUsername(Long.toString(req.getProfessorNumber()))) {
            return ("Fail");
        } else {
            User user = new User();
            user.setTypeOfUser("Professor");
            Professor professor = new Professor(req.getProfessorNumber(), req.getFirstName(), req.getLastName(), req.getDepartment(), req.getPosition(), req.getField());
            Role userRole = roleRepository.findByName("ROLE_PROFESSOR");
            user.setRoles(Collections.singleton(userRole));
            user.setUsername(Long.toString(professor.getProfessorNumber()));
            user.setPassword(passwordEncoder.encode(req.getPassword()));
            professorRepository.save(professor);
            user.setFK(professor.getId());
            userRepository.save(user);
            return ("success");
        }
    }

//____________________________________________________________________________________________________________________________

    // Registering new employee

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @CrossOrigin(origins = "http://localhost:63343")
    @RequestMapping(value = "/EmployeeSignUp", method = RequestMethod.POST)
    public String EmployeeSignUp(@RequestBody EmployeeRequest req) {
        if (userRepository.existsByUsername(Long.toString(req.getEmployeeNumber()))) {
            return ("Fail");
        } else {
            User user = new User();
            user.setTypeOfUser("Employee");
            FacultyEmployee employee = new FacultyEmployee(req.getEmployeeNumber(), req.getFirstName(), req.getLastName());
            Role userRole = roleRepository.findByName("ROLE_EMPLOYEE");
            user.setRoles(Collections.singleton(userRole));
            user.setUsername(Long.toString(employee.getEmployeeNumber()));
            user.setPassword(passwordEncoder.encode(req.getPassword()));
            facultyEmployeeRepository.save(employee);
            user.setFK(employee.getId());
            userRepository.save(user);
            return ("success");
        }
    }
}
//____________________________________________________________________________________________________________________________