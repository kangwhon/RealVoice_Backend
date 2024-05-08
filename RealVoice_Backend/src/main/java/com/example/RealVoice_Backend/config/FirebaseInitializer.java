package com.example.RealVoice_Backend.config;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Component
public class FirebaseInitializer {
    
    private static final Logger logger = LoggerFactory.getLogger(FirebaseInitializer.class);
    
    @PostConstruct
    public void initializeFirebase() {
        try {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.getApplicationDefault())
                    .build();

            FirebaseApp.initializeApp(options);
            
            // Firebase가 성공적으로 초기화되었음을 로그로 출력합니다.
            logger.info("Firebase initialized successfully.");
        } catch (IOException e) {
            // Firebase 초기화 중 오류가 발생한 경우 예외를 출력합니다.
            logger.error("Error initializing Firebase:", e);
        }
    }
}