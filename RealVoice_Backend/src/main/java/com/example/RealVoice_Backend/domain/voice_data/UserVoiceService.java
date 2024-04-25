package com.example.RealVoice_Backend.domain.voice_data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.RealVoice_Backend.domain.user.User;
import com.example.RealVoice_Backend.domain.user.UserRepository;
import com.example.RealVoice_Backend.domain.voice_data.UserVoice;
import com.example.RealVoice_Backend.domain.voice_data.UserVoiceRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UserVoiceService {

    @Autowired
    private UserVoiceRepository userVoiceRepository;

    @Autowired
    private UserRepository userRepository;

    public void saveUserVoice(String userId, MultipartFile voiceFile) throws IOException {
        // 사용자 ID로 사용자 정보 조회
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            // 파일 저장 경로 설정
            String uploadDir = "user-voices"; // 파일을 저장할 디렉토리 설정
            String fileName = StringUtils.cleanPath(voiceFile.getOriginalFilename());
            Path uploadPath = Paths.get(uploadDir);

            // 디렉토리가 없을 경우 생성
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 파일 저장
            Path filePath = uploadPath.resolve(fileName).normalize();
            Files.copy(voiceFile.getInputStream(), filePath);

            // 파일 경로로 음성 데이터 저장
            UserVoice userVoice = new UserVoice(user.getId(), filePath.toString());
            userVoiceRepository.save(userVoice);
        } else {
            // 사용자 정보가 없을 경우 처리 (예외 처리 등)
            System.out.println("User not found for ID: " + userId);
        }
    }

    public UserVoice getUserVoiceById(String userVoiceId) {
        return userVoiceRepository.findById(userVoiceId).orElse(null);
    }

    public void deleteUserVoice(String userVoiceId) {
        // 음성 데이터 ID로 음성 데이터 조회
        UserVoice userVoice = userVoiceRepository.findById(userVoiceId).orElse(null);
        if (userVoice != null) {
            // 음성 파일 삭제
            String filePath = userVoice.getFilePath();
            if (filePath != null) {
                try {
                    Files.deleteIfExists(Paths.get(filePath));
                } catch (IOException e) {
                    e.printStackTrace(); // 파일 삭제 중 오류 발생 시 로그 출력
                }
            }

            // 음성 데이터 삭제
            userVoiceRepository.delete(userVoice);
        } else {
            System.out.println("User voice not found for ID: " + userVoiceId);
        }
    }
}