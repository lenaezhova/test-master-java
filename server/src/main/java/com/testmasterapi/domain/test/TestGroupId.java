package com.testmasterapi.domain.test;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestGroupId implements Serializable {
    private Long testId;
    private Long groupId;
}