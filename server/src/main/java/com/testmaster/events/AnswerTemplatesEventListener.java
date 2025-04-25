package com.testmaster.events;

import com.testmaster.service.QuestionService.QuestionService;
import com.testmasterapi.domain.answerTemplate.event.AnswerTemplateEvent;
import com.testmasterapi.domain.question.request.QuestionUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerTemplatesEventListener {

    private final QuestionService questionService;

    @Transactional
    @EventListener
    public void onAnswerTemplateEvent(AnswerTemplateEvent event) {
        var answerTemplate = event.answerTemplate();
        var question = answerTemplate.getQuestion();

        if (question != null) {
            var request = new QuestionUpdateRequest();
            questionService.update(question.getId(), request);
        }
    }
}
