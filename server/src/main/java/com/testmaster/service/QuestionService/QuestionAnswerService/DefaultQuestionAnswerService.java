package com.testmaster.service.QuestionService.QuestionAnswerService;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.AnswerMapper;
import com.testmaster.model.Answer;
import com.testmaster.repository.AnswerRepository.AnswerRepository;
import com.testmasterapi.domain.answer.data.AnswerData;
import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
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

    @NotNull
    @Override
    public PageData<AnswerData> getAllAnswers(Long questionId, Boolean showDeletedQuestion, @NotNull Pageable pageable) {
        var content = answerRepository.findAllByQuestionId(questionId, showDeletedQuestion, pageable)
                .stream()
                .map(answerMapper::toData)
                .toList();

        LongSupplier total = () -> answerRepository.countAllByQuestionId(questionId, showDeletedQuestion);

        Page<AnswerData> page = PageableExecutionUtils.getPage(content, pageable, total);

        return PageData.fromPage(page);
    }

    @Override
    @Transactional
    public void deleteAllAnswers(Long questionId) {
        int deleted = answerRepository.deleteAllByQuestionId(questionId);
        if (deleted == 0) {
            String notFoundQuestionMessage = "Вопрос не найден";
            throw new NotFoundException(notFoundQuestionMessage);
        }
    }
}
