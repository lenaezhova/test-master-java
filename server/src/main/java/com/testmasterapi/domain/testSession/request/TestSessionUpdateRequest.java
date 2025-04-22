package com.testmasterapi.domain.testSession.request;

import com.testmasterapi.domain.testSession.TestSessionStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TestSessionUpdateRequest {
    private TestSessionStatus status;
    private Integer countPoints;
    private LocalDateTime closedAt;
}
