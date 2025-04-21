package com.testmasterapi.domain.test.request;

import com.testmasterapi.domain.test.TestStatus;
import lombok.Data;

@Data
public class TestUpdateStatusRequest {
    private TestStatus status;
}
