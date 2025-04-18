package com.testmasterapi.domain.test.request;

import com.testmasterapi.domain.test.TestStatus;
import lombok.Data;

@Data
public class TestUpdateRequest {
    private TestStatus status;
    private String title;
    private String description;
}
