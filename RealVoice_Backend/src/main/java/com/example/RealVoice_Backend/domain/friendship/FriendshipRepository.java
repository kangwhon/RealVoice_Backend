package com.example.RealVoice_Backend.domain.friendship;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FriendshipRepository extends MongoRepository<Friendship, String> {

    List<Friendship> findByUserId(String userId);

    List<Friendship> findByFriendId(String friendId);

    Friendship findByUserIdAndFriendId(String userId, String friendId);

    void deleteByUserIdAndFriendId(String userId, String friendId);
}
