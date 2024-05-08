package com.example.RealVoice_Backend.domain.user;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByPhoneNumber(String phoneNumber);
}