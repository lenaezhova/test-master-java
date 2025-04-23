package com.testmaster.service.TestService.TestQuestionService;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.QuestionMapper;
import com.testmaster.mapper.TestMapper;
import com.testmaster.model.Test.Test;
import com.testmaster.model.User.User;
import com.testmaster.repository.QuestionRepository.QuestionRepository;
import com.testmaster.repository.TestRepository.TestRepository;
import com.testmaster.repository.UserRepository.UserRepository;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionCreateRequest;
import com.testmasterapi.domain.test.data.TestData;
import com.testmasterapi.domain.test.request.TestCreateRequest;
import com.testmasterapi.domain.test.request.TestUpdateRequest;
import com.testmasterapi.domain.user.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultTestQuestionService implements TestQuestionService {
    private final QuestionMapper questionMapper;

    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;

    private final String notFoundTestMessage = "Тест не найден";

    @Override
    public List<QuestionData> getAllQuestions(Long testId) {
        return questionRepository.findAllByTestId(testId)
                .stream()
                .map(questionMapper::toData)
                .toList();
    }

    @NotNull
    @Transactional
    @Override
    public QuestionData createQuestion(Long testId, @NotNull QuestionCreateRequest request) {
        var test = this.getTest(testId);

        var question = questionMapper.toEntity(request, test);

        questionRepository.save(question);
        return questionMapper.toData(question);
    }

    @Override
    @Transactional
    public void deleteAllQuestions(Long testId) {
        int deleted = questionRepository.deleteAllByTestId(testId);
        if (deleted == 0) {
            throw new NotFoundException("Вопрос не найден");
        }
    }

    private Test getTest(Long testId) {
        return testRepository.findById(testId)
                .orElseThrow(() -> new NotFoundException(notFoundTestMessage));
    }
}
