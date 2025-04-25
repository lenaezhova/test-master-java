package com.testmaster.service.TestService.TestGroupService;

import com.testmaster.exeption.ClientException;
import com.testmaster.exeption.NotFoundException;
import com.testmaster.model.Group;
import com.testmaster.model.Test.Test;
import com.testmaster.model.Test.TestGroup;
import com.testmaster.repository.GroupRepository.GroupRepository;
import com.testmaster.repository.TestGroupRepository;
import com.testmaster.repository.TestRepository.TestRepository;
import com.testmasterapi.domain.test.TestGroupId;
import com.testmasterapi.domain.group.request.TestsGroupAddRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultTestGroupService implements TestGroupService {
    private final TestGroupRepository groupTestRepository;
    private final TestRepository testRepository;
    private final GroupRepository groupRepository;

    @Transactional
    @Override
    public void addTest(Long testId, Long groupId, TestsGroupAddRequest request) {
        if (groupTestRepository.existsByTest_Id(testId)) {
            throw new ClientException("Тест уже находится в группе", HttpStatus.CONFLICT.value());
        }

        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new NotFoundException("Тест не найден"));

        Group group = groupRepository
                .findById(groupId)
                .orElseThrow(() -> new NotFoundException("Группа не найдена"));

        TestGroupId id = new TestGroupId(
                test.getId(),
                group.getId()
        );

        TestGroup data = new TestGroup();
        data.setId(id);
        data.setTest(test);
        data.setGroup(group);
        groupTestRepository.save(data);
    }

    @Override
    public void deleteTest(Long testId, Long groupId) {
        int deleted = groupTestRepository.delete(testId, groupId);
        if (deleted == 0) {
            throw new NotFoundException("Группа (тест) с таким идентификатором не найдены");
        }
    }
}
