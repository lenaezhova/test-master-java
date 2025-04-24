package com.testmaster.service.QuestionService;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.QuestionMapper;
import com.testmaster.model.Question;
import com.testmaster.repository.QuestionRepository.QuestionRepository;
import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionUpdateRequest;
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
public class DefaultQuestionService implements QuestionService {
    private final QuestionMapper questionMapper;

    private final QuestionRepository questionRepository;

    private final String notFoundQuestionMessage = "Вопрос не найден";

    @NotNull
    @Override
    public PageData<QuestionData> getAll(Boolean showDeleted, @NotNull Pageable pageable) {
        var content = questionRepository.findAllQuestions(showDeleted, pageable)
                .stream()
                .map(questionMapper::toData)
                .toList();

        LongSupplier total = () -> questionRepository.countAllQuestions(showDeleted);

        Page<QuestionData> page = PageableExecutionUtils.getPage(content, pageable, total);

        return PageData.fromPage(page);
    }

    @Override
    public QuestionData getOne(Long id) {
        return questionMapper.toData(this.getQuestion(id));
    }

    @Override
    @Transactional
    public void update(Long questionId, QuestionUpdateRequest request) {
        int updated = questionRepository.update(questionId, request);
        if (updated == 0) {
            throw new NotFoundException(notFoundQuestionMessage);
        }
    }

    private Question getQuestion(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException(notFoundQuestionMessage));
    }
}
