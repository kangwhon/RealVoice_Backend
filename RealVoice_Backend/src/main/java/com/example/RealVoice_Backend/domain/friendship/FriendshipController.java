package com.example.RealVoice_Backend.domain.friendship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/friends")
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;

    @PostMapping("/{userId}/add")
    public ResponseEntity<String> addFriend(
            @PathVariable String userId,
            @RequestParam("friendId") String friendId
    ) {
        friendshipService.addFriend(userId, friendId);
        return ResponseEntity.ok("Friend added successfully.");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Friendship>> getFriends(
            @PathVariable String userId
    ) {
        List<Friendship> friends = friendshipService.getFriends(userId);
        return ResponseEntity.ok(friends);
    }
}
