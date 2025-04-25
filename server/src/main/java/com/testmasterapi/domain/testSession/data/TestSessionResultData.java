package com.testmasterapi.domain.testSession.data;

import com.testmasterapi.domain.question.data.QuestionResultData;
import com.testmasterapi.domain.user.data.UserData;
import com.testmasterapi.domain.user.data.UserTestSessionData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class TestSessionResultData {
    private Long id;
    private Integer countPoints;
    private UserTestSessionData user;
    private List<QuestionResultData> questions;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;
}
