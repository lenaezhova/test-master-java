package com.testmaster.sheduler;

import com.testmaster.service.BackupService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class BackupScheduler {

    private final BackupService backupService;

    @Scheduled(cron = "0 0 3 * * MON")
    public void performBackup() {
        try {
            File file = createPostgresBackup();
            backupService.uploadBackup(file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File createPostgresBackup() throws IOException, InterruptedException {
        String filename = "backup-" + LocalDate.now() + ".sql";
        File file = new File(filename);

        ProcessBuilder pb = new ProcessBuilder(
                "pg_dump",
                "-h", "test-master-db",
                "-p", "5432",
                "-U", "postgres",
                "-d", "test-master-db",
                "-f", file.getAbsolutePath()
        );
        pb.environment().put("PGPASSWORD", "postgres");
        Process process = pb.start();
        process.waitFor();

        return file;
    }
}
