package com.example.RealVoice_Backend.domain.user;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 서버 담당
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
    private UserRepository userRepository;

	@GetMapping("/register")
    public String showRegistrationForm() {
        return "Registration Form";
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        user.setCreated_at(new Date());
        userRepository.save(user);
        return "User Registered Successfully";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "Login Form";
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            return "Login Successful";
        } else {
            return "Login Failed";
        }
    }

    @GetMapping("/home")
    public String showHomePage() {
        return "Home Page";
    }

    @GetMapping("/all")
    public List<User> listUsers() {
        return userRepository.findAll();
    }
}
