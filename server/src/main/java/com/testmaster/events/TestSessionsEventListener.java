package com.testmaster.events;

import com.testmaster.service.TestSessionService.TestSessionService;
import com.testmasterapi.domain.testSession.event.TestSessionEvent;
import com.testmasterapi.domain.testSession.event.TestSessionEventsType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestSessionsEventListener {

    private final TestSessionService testSessionService;

    @Transactional
    @EventListener
    public void onTestSessionEvent(TestSessionEvent event) {
        var testSession = event.testSession();
        var type = event.eventsType();

        if (type == TestSessionEventsType.CLOSE) {
            testSessionService.close(testSession.getId());
        }
    }
}
