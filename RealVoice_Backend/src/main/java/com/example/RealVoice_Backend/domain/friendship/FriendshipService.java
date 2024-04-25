package com.example.RealVoice_Backend.domain.friendship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipService {

    @Autowired
    private FriendshipRepository friendshipRepository;

    public void addFriend(String userId, String friendId) {
        Friendship friendship = new Friendship(userId, friendId);
        friendshipRepository.save(friendship);
    }

    public List<Friendship> getFriends(String userId) {
        return friendshipRepository.findByUserId(userId);
    }
}
