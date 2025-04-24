package com.testmaster.events;

import com.testmaster.service.TestService.TestService;
import com.testmaster.service.TestService.TestTestsSessionsService.TestTestsSessionsService;
import com.testmaster.service.TestSessionService.TestSessionService;
import com.testmasterapi.domain.test.event.TestClosedEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestClosedEventListener {

    private final TestTestsSessionsService testTestsSessionsService;

    @Transactional
    @EventListener
    public void onTestClose(TestClosedEvent event) {
        testTestsSessionsService.closeAllOpenedSessions(event.testId());
    }
}
