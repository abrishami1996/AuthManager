package com.authenticationmanager.authmanager.Controller;

import com.authenticationmanager.authmanager.Model.Role;
import com.authenticationmanager.authmanager.Model.User;
import com.authenticationmanager.authmanager.payload.JwtAuthenticationResponse;
import com.authenticationmanager.authmanager.payload.LoginRequest;
import com.authenticationmanager.authmanager.repository.RoleRepository;
import com.authenticationmanager.authmanager.repository.UserRepository;
import com.authenticationmanager.authmanager.Security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
public class LoginController{

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;



    @CrossOrigin(origins = "http://localhost:63343")
    @GetMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }


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
