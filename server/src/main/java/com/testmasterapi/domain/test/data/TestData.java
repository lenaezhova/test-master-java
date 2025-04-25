package com.testmasterapi.domain.test.data;

import com.testmasterapi.domain.test.TestStatus;
import com.testmasterapi.domain.user.data.UserData;
import com.testmasterapi.domain.user.data.UserOwnerData;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class TestData extends BaseTestData {
    private UserOwnerData owner;
    private TestStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}