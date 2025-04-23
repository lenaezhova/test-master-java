package com.testmaster.service.QuestionService.QuestionAnswerService;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.AnswerMapper;
import com.testmaster.mapper.AnswerTemplateMapper;
import com.testmaster.mapper.QuestionMapper;
import com.testmaster.model.AnswerTemplate;
import com.testmaster.model.Question;
import com.testmaster.repository.AnswerRepository.AnswerRepository;
import com.testmaster.repository.AnswerTemplateRepository.AnswerTemplateRepository;
import com.testmaster.repository.QuestionRepository.QuestionRepository;
import com.testmaster.service.QuestionService.QuestionService;
import com.testmasterapi.domain.answer.data.AnswerData;
import com.testmasterapi.domain.answerTemplate.data.AnswerTemplateData;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateCreateRequest;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultQuestionAnswerService implements QuestionAnswerService {
    private final AnswerMapper answerMapper;

    private final AnswerRepository answerRepository;

    @Override
    public List<AnswerData> getAllAnswers(Long questionId) {
        return answerRepository.findAllByQuestionId(questionId)
                .stream()
                .map(answerMapper::toData)
                .toList();
    }

    @Override
    @Transactional
    public void deleteAllAnswers(Long questionId) {
        int deleted = answerRepository.deleteAllByQuestionId(questionId);
        if (deleted == 0) {
            throw new NotFoundException("Вопрос не найден");
        }
    }
}
