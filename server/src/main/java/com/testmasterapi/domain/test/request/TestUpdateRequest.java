package com.testmasterapi.domain.test.request;

import com.testmasterapi.domain.test.TestStatus;
import lombok.Data;

@Data
public class TestUpdateRequest {
    private String title;
    private String description;
    private Boolean deleted;
    private TestStatus status;
}
