package com.testmaster.dto;

import api.domain.test.TestStatus;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestDto {
    private Long id;
    private UserDto owner;
    private String title;
    private TestStatus status;
    private String description;
}