package com.testmaster.service.QuestionService;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.QuestionMapper;
import com.testmaster.model.Question;
import com.testmaster.repository.QuestionRepository.QuestionRepository;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultQuestionService implements QuestionService {
    private final QuestionMapper questionMapper;

    private final QuestionRepository questionRepository;

    private final String notFoundQuestionMessage = "Вопрос не найден";

    @Override
    public List<QuestionData> getAll() {
        return questionRepository.findAll()
                .stream()
                .map(questionMapper::toData)
                .toList();
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
