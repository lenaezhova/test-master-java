package com.testmaster.service.QuestionService;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.QuestionMapper;
import com.testmaster.model.Question;
import com.testmaster.repository.QuestionRepository.QuestionRepository;
import com.testmaster.repository.QuestionTypeRepository.QuestionTypeRepository;
import com.testmaster.repository.TestRepository.TestRepository;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionCreateRequest;
import com.testmasterapi.domain.question.request.QuestionUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultQuestionService implements QuestionService {
    private final QuestionMapper questionMapper;

    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;
    private final QuestionTypeRepository questionTypeRepository;

    @Override
    public List<QuestionData> getAll() {
        return questionRepository
                .findAll()
                .stream()
                .map(questionMapper::toQuestionData)
                .toList();
    }

    @Override
    public QuestionData getOne(Long id) {
        var data = questionRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Вопрос не найден"));

        return questionMapper.toQuestionData(data);
    }

    @NotNull
    @Transactional
    @Override
    public QuestionData create(@NotNull QuestionCreateRequest request) {

        var test = testRepository
                .findById(request.testId())
                .orElseThrow(() -> new NotFoundException("Тест не найден"));

        var questionType = questionTypeRepository
                .findById(request.typeId())
                .orElseThrow(() -> new NotFoundException("Тип вопроса не найден"));

        Question question = questionMapper.toEntity(request, test, questionType);

        questionRepository.save(question);
        return questionMapper.toQuestionData(question);
    }

    @Override
    @Transactional
    public void update(Long questionId, QuestionUpdateRequest request) {
        int updated = questionRepository.update(questionId, request);
        if (updated == 0) {
            throw new NotFoundException("Вопрос не найден");
        }
    }

    @Override
    @Transactional
    public void delete(Long questionId) {
        int deleted = questionRepository.delete(questionId);
        if (deleted == 0) {
            throw new NotFoundException("Вопрос не найден");
        }
    }
}
