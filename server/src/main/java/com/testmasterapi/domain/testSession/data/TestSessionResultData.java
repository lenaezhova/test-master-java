package com.testmasterapi.domain.testSession.data;

import com.testmasterapi.domain.question.data.QuestionResultData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class TestSessionResultData {
    private Long id;
    private Integer countPoints;
    private List<QuestionResultData> questions;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;
}
