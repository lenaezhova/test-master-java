package com.testmaster.service;

import com.testmasterapi.domain.minio.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BackupService {

    private final MinioClient minioClient;
    private final MinioProperties props;

    public void uploadBackup(File file) throws Exception {
        String objectName = "backup-" + LocalDate.now() + ".sql";

        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(props.getBucket()).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(props.getBucket()).build());
        }

        minioClient.uploadObject(UploadObjectArgs.builder()
                .bucket(props.getBucket())
                .object(objectName)
                .filename(file.getAbsolutePath())
                .contentType("application/sql")
                .build());
    }
}
