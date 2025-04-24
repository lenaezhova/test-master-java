package com.testmaster.events;

import com.testmaster.service.TestService.TestQuestionService.DefaultTestQuestionService;
import com.testmaster.service.TestService.TestQuestionService.TestQuestionService;
import com.testmasterapi.domain.test.event.TestDeletedEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDeletedEventListener {

    private final TestQuestionService testQuestionService;

    @Transactional
    @EventListener
    public void onTestDelete(TestDeletedEvent event) {
        testQuestionService.deleteAllQuestions(event.testId());
    }
}
