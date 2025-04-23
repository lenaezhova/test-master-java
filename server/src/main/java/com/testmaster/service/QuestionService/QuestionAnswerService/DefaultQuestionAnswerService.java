package com.testmaster.service.QuestionService.QuestionAnswerService;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.AnswerMapper;
import com.testmaster.repository.AnswerRepository.AnswerRepository;
import com.testmasterapi.domain.answer.data.AnswerData;
import lombok.RequiredArgsConstructor;
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
            String notFoundQuestionMessage = "Вопрос не найден";
            throw new NotFoundException(notFoundQuestionMessage);
        }
    }
}
