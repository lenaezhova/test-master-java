package com.testmaster.controller;

import com.testmaster.sheduler.BackupScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/backup")
public class BackupController {

    private final BackupScheduler backupScheduler;

    @GetMapping()
    public ResponseEntity<String> backupNow() {
        backupScheduler.performBackup();
        return ResponseEntity.ok("Backup started");
    }
}