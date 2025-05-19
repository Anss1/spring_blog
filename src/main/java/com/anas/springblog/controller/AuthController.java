package com.anas.springblog.controller;

import com.anas.springblog.dto.AuthRequest;
import com.anas.springblog.model.User;
import com.anas.springblog.service.UserService;
import com.anas.springblog.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return ResponseEntity.ok("registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest){

//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            authRequest.getUsername(),
//                            authRequest.getPassword()
//                    )
//            );
            if (authenticate(authRequest.getUsername(),authRequest.getPassword())){
                String token = jwtUtil.generateToken(authRequest.getUsername());

                return ResponseEntity.ok(token);
            }
            throw new BadCredentialsException("Invalid username or password");
    }

    private boolean authenticate(String username, String password){
        UserDetails userDetails = userService.loadUserByUsername(username);
        return passwordEncoder.matches(password,userDetails.getPassword());
    }
}
