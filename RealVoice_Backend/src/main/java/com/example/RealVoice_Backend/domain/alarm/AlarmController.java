package com.example.RealVoice_Backend.domain.alarm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alarms")
public class AlarmController {
    private final AlarmService alarmService;

    @Autowired
    public AlarmController(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @GetMapping
    public List<Alarm> getAllAlarms() {
        return alarmService.getAllAlarms();
    }

    @PostMapping
    public ResponseEntity<Alarm> addAlarm(@RequestBody Alarm alarm) {
        Alarm savedAlarm = alarmService.addAlarm(alarm);
        return new ResponseEntity<>(savedAlarm, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alarm> getAlarmById(@PathVariable String id) {
        Alarm alarm = alarmService.getAlarmById(id);
        if (alarm != null) {
            return new ResponseEntity<>(alarm, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlarm(@PathVariable String id) {
        alarmService.deleteAlarm(id);
        return ResponseEntity.noContent().build();
    }
}
