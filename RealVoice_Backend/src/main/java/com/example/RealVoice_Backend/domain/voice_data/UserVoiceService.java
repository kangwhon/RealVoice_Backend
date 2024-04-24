package com.example.RealVoice_Backend.domain.voice_data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.RealVoice_Backend.domain.user.User;
import com.example.RealVoice_Backend.domain.user.UserRepository;

@Service
public class UserVoiceService {

    @Autowired
    private UserVoiceRepository userVoiceRepository;

    @Autowired
    private UserRepository userRepository;

    public void saveUserVoice(String userId, String voiceId) {
        // 사용자 ID로 사용자 정보 조회
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            // 사용자 정보가 있을 경우 음성 데이터 저장
            UserVoice userVoice = new UserVoice(user.getId(), voiceId);
            userVoiceRepository.save(userVoice);
        } else {
            // 사용자 정보가 없을 경우 처리 (예외 처리 등)
            System.out.println("User not found for ID: " + userId);
        }
    }

    public UserVoice getUserVoiceById(String userVoiceId) {
        return userVoiceRepository.findById(userVoiceId).orElse(null);
    }

    // 필요에 따라 음성 데이터 삭제 등의 메서드 추가 가능
}