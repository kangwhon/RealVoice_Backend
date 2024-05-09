package com.example.RealVoice_Backend.domain.alarm;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final PushNotificationService pushNotificationService;

    @Autowired
    public AlarmService(AlarmRepository alarmRepository, PushNotificationService pushNotificationService) {
        this.alarmRepository = alarmRepository;
        this.pushNotificationService = pushNotificationService;
    }

    @Scheduled(fixedRate = 60000) // Check every minute
    public void checkAlarms() {
        LocalTime now = LocalTime.now();
        List<Alarm> alarms = alarmRepository.findAll();

        for (Alarm alarm : alarms) {
            LocalTime alarmTime = LocalTime.parse(alarm.getTime());
            if (now.equals(alarmTime)) {
                // Trigger the alarm notification
                pushNotificationService.sendPushNotification(alarm.getContent());
            }
        }
    }

    public List<Alarm> getAllAlarms() {
        return alarmRepository.findAll();
    }

    public Alarm addAlarm(Alarm alarm) {
        return alarmRepository.save(alarm);
    }

    public Alarm getAlarmById(String id) {
        return alarmRepository.findById(id).orElse(null);
    }

    public void deleteAlarm(String id) {
        alarmRepository.deleteById(id);
    }
}
