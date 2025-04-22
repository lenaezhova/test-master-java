package com.testmasterapi.domain.test.data;

import lombok.Data;

@Data
public class TestForSessionData {
    private Long id;
    private String title;
    private String description;
    private Boolean deleted;
}
