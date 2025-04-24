package com.testmaster.events;

import com.testmaster.service.TestService.TestTestsSessionsService.TestTestsSessionsService;
import com.testmaster.service.TestSessionService.TestSessionService;
import com.testmasterapi.domain.test.event.TestClosedEvent;
import com.testmasterapi.domain.testSession.TestSessionStatus;
import com.testmasterapi.domain.testSession.event.TestSessionClosedEvent;
import com.testmasterapi.domain.testSession.request.TestSessionUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestSessionClosedEventListener {

    private final TestSessionService testSessionService;

    @Transactional
    @EventListener
    public void onTestSessionClose(TestSessionClosedEvent event) {
        testSessionService.close(event.testSessionId());
    }
}
