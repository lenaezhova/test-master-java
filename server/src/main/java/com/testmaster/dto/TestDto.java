package com.testmaster.dto;

import com.testmaster.model.TestModel.TestModel;
import com.testmaster.model.TestModel.TestStatus;
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