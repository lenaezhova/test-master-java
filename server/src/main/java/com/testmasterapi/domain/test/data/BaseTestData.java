package com.testmasterapi.domain.test.data;

import com.testmasterapi.domain.test.TestStatus;
import com.testmasterapi.domain.user.data.UserOwnerData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BaseTestData {
    private Long id;
    private String title;
    private String description;
    private Boolean deleted;
}