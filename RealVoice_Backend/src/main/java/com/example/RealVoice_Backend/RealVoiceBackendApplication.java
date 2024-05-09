package com.example.RealVoice_Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RealVoiceBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealVoiceBackendApplication.class, args);
	}

}
