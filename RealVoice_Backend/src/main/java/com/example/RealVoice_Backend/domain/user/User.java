package com.example.RealVoice_Backend.domain.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "users") // 실제 몽고 DB 컬렉션 이름 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
    private String id; // 여기서 id는 mongoDB에서 객체를 식별하기 위한 id
    private String username; // 사용자 id
    private String fullName; // 이름
    private String email; // 이메일
    private String password; // 비밀번호
}
