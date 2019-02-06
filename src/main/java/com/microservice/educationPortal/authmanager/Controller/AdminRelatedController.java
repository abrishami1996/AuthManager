package com.microservice.educationPortal.authmanager.Controller;
import com.microservice.educationPortal.authmanager.repository.*;
import com.microservice.educationPortal.authmanager.Model.Role;
import com.microservice.educationPortal.authmanager.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    RoleRepository roleRepository;

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


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/addRole")
    public String AddRole(@RequestParam String username, @RequestParam String role){

        if (userRepository.existsByUsername(username)) {
            User user=userRepository.findByUsername(username);
            System.out.println(user.getUsername());
            Role theNewRole = roleRepository.findByName(role);
            if(theNewRole == null){
                return "role not found";
            }
            System.out.println(theNewRole);
            if (! user.getRoles().contains(theNewRole)){
                user.getRoles().add(theNewRole);
                userRepository.save(user);
                return "ok";
            }
            else{
                return "کاربر نقش را داشته است";
            }
        }
        return "user not found";

    }
}
