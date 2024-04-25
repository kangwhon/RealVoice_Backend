package com.example.RealVoice_Backend.domain.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        String message = userService.registerUser(user);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        String message = userService.loginUser(user);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{userId}/picture")
    public ResponseEntity<String> uploadProfilePicture(
            @PathVariable String userId,
            @RequestParam("file") MultipartFile file
    ) {
        ResponseEntity<String> responseEntity = userService.uploadProfilePicture(userId, file);
        return responseEntity;
    }
    
    @GetMapping("/{userId}/picture")
    public ResponseEntity<Resource> getProfilePicture(
            @PathVariable String userId
    ) {
        ResponseEntity<Resource> responseEntity = userService.getProfilePicture(userId);
        return responseEntity;
    }

    @DeleteMapping("/{userId}/picture")
    public ResponseEntity<String> deleteProfilePicture(@PathVariable String userId) {
        ResponseEntity<String> responseEntity = userService.deleteProfilePicture(userId);
        return responseEntity;
    }
}
