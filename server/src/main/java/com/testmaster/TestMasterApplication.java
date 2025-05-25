package com.testmaster;

import com.testmaster.config.properties.AuthProperties;
import com.testmasterapi.domain.minio.MinioProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties({
        MinioProperties.class,
        AuthProperties.class,
})
public class TestMasterApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestMasterApplication.class, args);
    }

}
