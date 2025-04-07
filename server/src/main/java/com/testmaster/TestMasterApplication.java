package com.testmaster;

import com.testmaster.config.properties.AuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        AuthProperties.class,
})
public class TestMasterApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestMasterApplication.class, args);
    }

}
