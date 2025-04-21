package com.testmasterapi.domain.test.request;

import com.testmasterapi.domain.test.TestStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestUpdateRequest extends TestUpdateStatusRequest {
    private String title;
    private String description;
    private Boolean deleted;
}
