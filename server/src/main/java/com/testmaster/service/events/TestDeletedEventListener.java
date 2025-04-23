package com.testmaster.service.events;

import com.testmaster.service.TestService.TestQuestionService.DefaultTestQuestionService;
import com.testmasterapi.domain.test.event.TestDeletedEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDeletedEventListener {

    private final DefaultTestQuestionService testQuestionService;

    @Transactional
    @EventListener
    public void onTestDeleted(TestDeletedEvent event) {
        testQuestionService.deleteAllQuestions(event.testId());
    }
}
