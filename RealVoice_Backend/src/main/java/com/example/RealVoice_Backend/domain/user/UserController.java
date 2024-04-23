package com.example.RealVoice_Backend.domain.user;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

// 서버 담당
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
    private UserRepository userRepository;
	
	@PostMapping("/{userId}/picture")
	public ResponseEntity<String> uploadProfilePicture(
	        @PathVariable String userId,
	        @RequestParam("file") MultipartFile file
	) throws IOException {
	    if (file.isEmpty()) {
	        return ResponseEntity.badRequest().body("Please select a file to upload.");
	    }

	    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	    String uploadDir = "profile-pictures/" + userId;

	    File directory = new File(uploadDir);
	    if (!directory.exists()) {
	        directory.mkdirs();
	    }

	    String filePath = uploadDir + "/" + fileName;
	    Path path = Paths.get(filePath);
	    Files.write(path, file.getBytes());

	    // URL은 클라우드 환경에 따라 변환하여 사용
	    String fileUrl = "/profile-pictures/" + userId + "/" + fileName;

	    User user = userRepository.findById(userId).orElse(null);
	    if (user != null) {
	        user.setUrl(fileUrl); // 저장된 파일의 URL로 업데이트
	        userRepository.save(user);
	        return ResponseEntity.ok("Profile picture uploaded successfully.");
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@GetMapping("/{userId}/picture")
    public ResponseEntity<byte[]> getProfilePicture(
            @PathVariable String userId
    ) throws IOException {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null && user.getUrl() != null) {
            // 이미지 파일을 바이트 배열로 읽어옴
            Path imagePath = Paths.get(user.getUrl());
            byte[] imageBytes = Files.readAllBytes(imagePath);

            // 이미지 바이트 배열과 HTTP 상태코드 200을 반환
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // 이미지 타입에 따라 CONTENT_TYPE 변경

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // 이미지 삭제 엔드포인트
    @DeleteMapping("/{userId}/picture")
    public ResponseEntity<String> deleteProfilePicture(
            @PathVariable String userId
    ) throws IOException {
        // 사용자 조회
        User user = userRepository.findById(userId).orElse(null);
        if (user == null || user.getUrl() == null) {
            return ResponseEntity.notFound().build();
        }

        // 이미지 파일 경로
        Path imagePath = Paths.get(user.getUrl());
        if (!Files.exists(imagePath)) {
            return ResponseEntity.notFound().build();
        }

        // 이미지 파일 삭제
        try {
            Files.delete(imagePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete profile picture.");
        }

        // User 엔터티의 URL 필드를 null로 업데이트
        user.setUrl(null);
        userRepository.save(user);

        return ResponseEntity.ok("Profile picture deleted successfully.");
    }

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
