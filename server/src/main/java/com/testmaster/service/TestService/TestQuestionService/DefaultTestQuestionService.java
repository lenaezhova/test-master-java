package com.testmaster.service.TestService.TestQuestionService;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.AnswerTemplateMapper;
import com.testmaster.mapper.QuestionMapper;
import com.testmaster.model.AnswerTemplate;
import com.testmaster.model.Question;
import com.testmaster.model.Test.Test;
import com.testmaster.repository.AnswerRepository.AnswerRepository;
import com.testmaster.repository.AnswerTemplateRepository.AnswerTemplateRepository;
import com.testmaster.repository.QuestionRepository.QuestionRepository;
import com.testmaster.repository.TestRepository.TestRepository;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionCreateRequest;
import com.testmasterapi.domain.question.request.QuestionCreateWithAnswersTemplatesRequest;
import com.testmasterapi.domain.question.request.QuestionUpdateWithAnswersTemplatesRequest;
import com.testmasterapi.domain.test.event.TestDeletedEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultTestQuestionService implements TestQuestionService {
    private final QuestionMapper questionMapper;
    private final AnswerTemplateMapper answerTemplateMapper;

    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final AnswerTemplateRepository answerTemplateRepository;

    private final String notFoundTestMessage = "Тест не найден";
    private final AnswerRepository answerRepository;

    @Override
    public List<QuestionData> getAllQuestions(Long testId, Boolean showQuestionSoftDeleted) {
        return questionRepository.findAllByTestId(testId, showQuestionSoftDeleted)
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

    @NotNull
    @Transactional
    @Override
    public QuestionData createQuestionWithTemplates(Long testId, @NotNull QuestionCreateWithAnswersTemplatesRequest request) {
        var test = this.getTest(testId);

        var question = questionMapper.toEntity(request, test);
        questionRepository.save(question);

        List<AnswerTemplate> answerTemplates = request.getAnswerTemplates()
                .stream()
                .map(template -> answerTemplateMapper.toEntity(template, question))
                .collect(Collectors.toList());
        answerTemplateRepository.saveAll(answerTemplates);

        return questionMapper.toData(question);
    }

    @Override
    @Transactional
    public void deleteAllQuestions(Long testId) {
        List<Long> questionIds = questionRepository.findAllByTestId(testId, true)
                .stream()
                .map(Question::getId)
                .toList();

        if (!questionIds.isEmpty()) {
            answerRepository.deleteAllByQuestionIds(questionIds);
            answerTemplateRepository.deleteAllByQuestionIds(questionIds);
            questionRepository.deleteByIds(questionIds);
        }
    }

    private Test getTest(Long testId) {
        return testRepository.findById(testId)
                .orElseThrow(() -> new NotFoundException(notFoundTestMessage));
    }
}
