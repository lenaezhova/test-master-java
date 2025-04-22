package com.testmasterapi.domain.testSession.data;

import com.testmaster.model.TestSession;
import com.testmasterapi.domain.test.data.TestForSessionData;
import com.testmasterapi.domain.testSession.TestSessionStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TestSessionData {
    private Long id;
    private TestSessionStatus status;
    private Integer countPoints;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;
    private TestForSessionData test;
}
