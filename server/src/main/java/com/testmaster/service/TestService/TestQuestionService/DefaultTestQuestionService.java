package com.testmaster.service.TestService.TestQuestionService;

import com.testmaster.exeption.ClientException;
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
import com.testmasterapi.domain.question.QuestionTypes;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.data.QuestionWithTemplatesData;
import com.testmasterapi.domain.question.event.QuestionEvent;
import com.testmasterapi.domain.question.event.QuestionEventsType;
import com.testmasterapi.domain.question.request.QuestionCreateRequest;
import com.testmasterapi.domain.question.request.QuestionCreateWithAnswersTemplatesRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
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
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public List<QuestionData> getAllQuestions(Long testId, Boolean showQuestionSoftDeleted) {
        return questionRepository.findAllByTestId(testId, showQuestionSoftDeleted)
                .stream()
                .map(questionMapper::toData)
                .toList();
    }

    @Override
    public List<QuestionWithTemplatesData> getAllQuestionsWithTemplates(Long testId, Boolean showQuestionSoftDeleted) {
        var questions = questionRepository.findAllByTestId(testId, showQuestionSoftDeleted);
        var questionIds = questions.stream()
                .map(Question::getId)
                .toList();

        var allTemplates = answerTemplateRepository.findAllByQuestionIds(questionIds);

        var templatesByQuestionId = allTemplates.stream()
                .collect(Collectors.groupingBy(t -> t.getQuestion().getId()));

        return questions.stream()
                .map(question -> {
                    var templates = templatesByQuestionId.getOrDefault(question.getId(), List.of());
                    return questionMapper.toDataWithTemplate(question, templates);
                })
                .toList();
    }

    @NotNull
    @Transactional
    @Override
    public QuestionData createQuestion(Long testId, @NotNull QuestionCreateRequest request) {
        applicationEventPublisher.publishEvent(new QuestionEvent(this.getQuestion(testId), QuestionEventsType.CREATE));

        var test = this.getTest(testId);

        var question = questionMapper.toEntity(request, test);

        questionRepository.save(question);
        return questionMapper.toData(question);
    }

    @NotNull
    @Transactional
    @Override
    public QuestionData createQuestionWithTemplates(Long testId, @NotNull QuestionCreateWithAnswersTemplatesRequest request) {
        applicationEventPublisher.publishEvent(new QuestionEvent(this.getQuestion(testId), QuestionEventsType.CREATE));

        var test = this.getTest(testId);

        var question = questionMapper.toEntity(request, test);
        questionRepository.save(question);

        if (question.getType() == QuestionTypes.TEXT && request.getAnswerTemplates().size() > 1) {
            throw new ClientException("В текстовом вопросе не может быть больше 1 варианта ответа", HttpStatus.CONFLICT.value());
        }

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
        applicationEventPublisher.publishEvent(new QuestionEvent(this.getQuestion(testId), QuestionEventsType.DELETE));

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

    private Question getQuestion(Long testId) {
        var test = this.getTest(testId);
        var question = new Question();
        question.setTest(test);
        return question;
    }

    private Test getTest(Long testId) {
        return testRepository.findById(testId)
                .orElseThrow(() -> new NotFoundException(notFoundTestMessage));
    }
}
