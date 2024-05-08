package com.example.RealVoice_Backend.domain.friendship;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "friendships")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Friendship {

    @Id
    private String id;

    private String userId;      // 사용자 ID
    private String friendId;    // 친구 ID

    public Friendship(String userId, String friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }
}