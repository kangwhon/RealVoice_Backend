package com.example.RealVoice_Backend.domain.user;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    private static final String UPLOAD_DIR = "profile-pictures/";

    public String registerUser(User user) {
        // 사용자가 이미 존재하는지 확인
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return "Username already exists";
        }

        // 가입 날짜 설정
        user.setCreated_at(new Date());
        userRepository.save(user);
        return "User Registered Successfully";
    }

    public String loginUser(User user) {
        // 사용자 확인
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            return "Login Successful";
        } else {
            return "Login Failed";
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId).orElse(null);
    }


    public ResponseEntity<String> uploadProfilePicture(String userId, MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload.");
        }

        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uploadDir = UPLOAD_DIR + userId + "/";

            Files.createDirectories(Paths.get(uploadDir));

            Path filePath = Paths.get(uploadDir + fileName);
            Files.write(filePath, file.getBytes());

            String fileUrl = uploadDir + fileName;

            User user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                user.setUrl(fileUrl);
                userRepository.save(user);
                return ResponseEntity.ok("Profile picture uploaded successfully.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to upload profile picture.");
        }
    }
    
    public ResponseEntity<Resource> getProfilePicture(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null || StringUtils.isEmpty(user.getUrl())) {
            return ResponseEntity.notFound().build();
        }

        try {
            Path imagePath = Paths.get(user.getUrl());
            byte[] imageBytes = Files.readAllBytes(imagePath);
            ByteArrayResource resource = new ByteArrayResource(imageBytes);

            return ResponseEntity.ok()
                                 .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                                 .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ByteArrayResource(new byte[0]));
        }
    }

    public ResponseEntity<String> deleteProfilePicture(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null || StringUtils.isEmpty(user.getUrl())) {
            return ResponseEntity.notFound().build();
        }

        try {
            Path imagePath = Paths.get(user.getUrl());
            Files.deleteIfExists(imagePath);

            user.setUrl(null);
            userRepository.save(user);

            return ResponseEntity.ok("Profile picture deleted successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to delete profile picture.");
        }
    }
  
}
