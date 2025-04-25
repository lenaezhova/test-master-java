package com.testmaster.events;

import com.testmaster.service.TestService.TestService;
import com.testmasterapi.domain.question.event.QuestionEvent;
import com.testmasterapi.domain.test.request.TestUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionsEventListener {

    private final TestService testService;

    @Transactional
    @EventListener
    public void onQuestionEvent(QuestionEvent event) {
        var question = event.question();
        var test = question.getTest();

        if (test != null) {
            var request = new TestUpdateRequest();
            testService.update(test.getId(), request);
        }
    }
}
