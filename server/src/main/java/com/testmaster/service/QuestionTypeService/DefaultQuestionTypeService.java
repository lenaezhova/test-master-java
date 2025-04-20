package com.testmaster.service.QuestionTypeService;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.QuestionTypeMapper;
import com.testmaster.model.QuestionType;
import com.testmaster.repository.QuestionTypeRepository.QuestionTypeRepository;
import com.testmasterapi.domain.question.data.QuestionTypeData;
import com.testmasterapi.domain.question.request.QuestionTypeCreateRequest;
import com.testmasterapi.domain.question.request.QuestionTypeUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultQuestionTypeService implements QuestionTypeService {
    private final QuestionTypeMapper questionTypeMapper;

    private final QuestionTypeRepository questionTypeRepository;

    @Override
    public List<QuestionTypeData> getAll() {
        return questionTypeRepository
                .findAll()
                .stream()
                .map(questionTypeMapper::toQuestionTypeData)
                .toList();
    }

    @Override
    public QuestionTypeData getOne(Long id) {
        var questionType = questionTypeRepository
                        .findById(id)
                        .orElseThrow(NotFoundException::new);

        return questionTypeMapper.toQuestionTypeData(questionType);
    }

    @NotNull
    @Transactional
    @Override
    public QuestionTypeData create(@NotNull QuestionTypeCreateRequest request) {
        QuestionType questionType = questionTypeMapper.toEntity(request);

        questionTypeRepository.save(questionType);
        return questionTypeMapper.toQuestionTypeData(questionType);
    }

    @Override
    @Transactional
    public void update(Long typeId, QuestionTypeUpdateRequest request) {
        System.out.println("------------------------------------------------------------");
        System.out.println(request);
        int updated = questionTypeRepository.update(typeId, request);
        if (updated == 0) {
            throw new NotFoundException();
        }
    }

    @Override
    @Transactional
    public void delete(Long typeId) {
        int deleted = questionTypeRepository.delete(typeId);
        if (deleted == 0) {
            throw new NotFoundException();
        }
    }
}
