package com.example.RealVoice_Backend.domain.voice_data;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user/voice")
public class UserVoiceController {

    @Autowired
    private UserVoiceService userVoiceService;

    @PostMapping("/upload/{userId}")
    public ResponseEntity<String> uploadUserVoice(
            @PathVariable String userId,
            @RequestParam("voiceFile") MultipartFile voiceFile
    ) {
        try {
            // 사용자 음성 데이터 저장
            userVoiceService.saveUserVoice(userId, voiceFile);
            return ResponseEntity.ok("User voice uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to upload user voice.");
        }
    }

    @GetMapping("/{userVoiceId}")
    public ResponseEntity<UserVoice> getUserVoiceById(@PathVariable String userVoiceId) {
        UserVoice userVoice = userVoiceService.getUserVoiceById(userVoiceId);
        if (userVoice != null) {
            return ResponseEntity.ok(userVoice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{userVoiceId}")
    public ResponseEntity<String> deleteUserVoice(@PathVariable String userVoiceId) {
        userVoiceService.deleteUserVoice(userVoiceId);
        return ResponseEntity.ok("User voice deleted successfully.");
    }
}