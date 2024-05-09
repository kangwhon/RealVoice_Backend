package com.example.RealVoice_Backend.domain.alarm;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlarmRepository extends MongoRepository<Alarm, String> {
}
