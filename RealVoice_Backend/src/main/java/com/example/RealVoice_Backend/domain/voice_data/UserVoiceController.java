package com.example.RealVoice_Backend.domain.voice_data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
            // 음성 파일 업로드 및 저장
            String voiceId = saveVoiceFile(voiceFile); // 음성 파일을 저장하고, 저장된 음성 파일 ID를 가져옴
            userVoiceService.saveUserVoice(userId, voiceId);
            return ResponseEntity.ok("User voice uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to upload user voice.");
        }
    }

    private String saveVoiceFile(MultipartFile voiceFile) throws IOException {
        // 실제 음성 파일 저장 로직 구현
        // 여기서는 단순히 파일을 저장하고, 저장된 파일의 ID를 반환하는 예시 코드
        // 실제 구현에서는 파일을 어떻게 저장할지에 따라 구현 필요
        // 예시: 파일을 저장하고, 저장된 파일의 ID를 반환
        return "generated-voice-id"; // 실제 저장된 파일 ID 또는 URL을 반환하도록 수정
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
}