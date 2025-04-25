package com.testmaster.service.QuestionService.QuestionAnswerService;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.AnswerMapper;
import com.testmaster.model.Answer;
import com.testmaster.model.Question;
import com.testmaster.repository.AnswerRepository.AnswerRepository;
import com.testmaster.repository.QuestionRepository.QuestionRepository;
import com.testmasterapi.domain.answer.data.AnswerData;
import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.question.event.QuestionEvent;
import com.testmasterapi.domain.question.event.QuestionEventsType;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.LongSupplier;

@Service
@RequiredArgsConstructor
public class DefaultQuestionAnswerService implements QuestionAnswerService {
    private final AnswerMapper answerMapper;

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @NotNull
    @Override
    public PageData<AnswerData> getAllAnswers(Long questionId, Boolean showOnlyDeletedQuestion, @NotNull Pageable pageable) {
        var content = answerRepository.findAllByQuestionId(questionId, showOnlyDeletedQuestion, pageable)
                .stream()
                .map(answerMapper::toData)
                .toList();

        LongSupplier total = () -> answerRepository.countAllByQuestionId(questionId, showOnlyDeletedQuestion);

        Page<AnswerData> page = PageableExecutionUtils.getPage(content, pageable, total);

        return PageData.fromPage(page);
    }

    @Override
    @Transactional
    public void deleteAllAnswers(Long questionId) {
        var question = this.getQuestion(questionId);
        applicationEventPublisher.publishEvent(new QuestionEvent(question, QuestionEventsType.UPDATE));

        int deleted = answerRepository.deleteAllByQuestionId(questionId);
        if (deleted == 0) {
            throw new NotFoundException("Вопрос не найден");
        }
    }

    private Question getQuestion(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException("Вопрос не найден"));
    }
}
