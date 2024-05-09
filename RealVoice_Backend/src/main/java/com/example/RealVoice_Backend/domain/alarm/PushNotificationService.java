package com.example.RealVoice_Backend.domain.alarm;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

@Service
public class PushNotificationService {

    public void sendPushNotification(String content) {
        Message message = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle("Alarm Notification")
                        .setBody(content)
                        .build())
                .setTopic("alarms") // Topic to which the notification will be sent
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (Exception e) {
            System.err.println("Failed to send message: " + e.getMessage());
        }
    }
}
