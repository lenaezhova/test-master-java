package com.testmaster.events;

import com.testmaster.service.TestSessionService.TestSessionService;
import com.testmasterapi.domain.answer.event.AnswerEvent;
import com.testmasterapi.domain.testSession.request.TestSessionUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswersEventListener {

    private final TestSessionService testSessionService;

    @Transactional
    @EventListener
    public void onAnswerEvent(AnswerEvent event) {
        var answer = event.answer();
        var testSession = answer.getTestSession();
        var type = event.eventsType();

        if (testSession != null) {
            var request = new TestSessionUpdateRequest();
            testSessionService.update(testSession.getId(), request);
        }
    }
}
