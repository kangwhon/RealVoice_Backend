package com.example.RealVoice_Backend.domain.voice_data;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserVoiceRepository extends MongoRepository<UserVoice, String> {
    // 추가적인 메서드가 필요한 경우 여기에 정의할 수 있습니다.
}