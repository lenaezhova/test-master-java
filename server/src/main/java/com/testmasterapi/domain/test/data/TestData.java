package com.testmasterapi.domain.test.data;

import com.testmasterapi.domain.test.TestStatus;
import com.testmasterapi.domain.user.data.UserData;
import com.testmasterapi.domain.user.data.UserOwnerData;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TestData {
    private Long id;
    private UserOwnerData owner;
    private String title;
    private TestStatus status;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}