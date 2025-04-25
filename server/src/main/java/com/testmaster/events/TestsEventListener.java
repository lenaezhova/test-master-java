package com.testmaster.events;

import com.testmaster.service.TestService.TestQuestionService.TestQuestionService;
import com.testmaster.service.TestService.TestTestsSessionsService.TestTestsSessionsService;
import com.testmasterapi.domain.test.event.TestEvent;
import com.testmasterapi.domain.test.event.TestEventsType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestsEventListener {

    private final TestTestsSessionsService testTestsSessionsService;
    private final TestQuestionService testQuestionService;

    @Transactional
    @EventListener
    public void onTestEvent(TestEvent event) {
        var test = event.test();
        var type = event.eventsType();

        if (type == TestEventsType.CLOSE) {
            testTestsSessionsService.closeAllOpenedSessions(test.getId());
        }
        if (type == TestEventsType.DELETE) {
            testQuestionService.deleteAllQuestions(test.getId());
        }
    }
}
