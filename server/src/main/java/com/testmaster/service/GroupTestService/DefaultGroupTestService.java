package com.testmaster.service.GroupTestService;

import com.testmaster.exeption.ClientException;
import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.TestMapper;
import com.testmaster.model.Group.Group;
import com.testmaster.model.Group.GroupTest;
import com.testmaster.model.Test;
import com.testmaster.repository.GroupRepository.GroupRepository;
import com.testmaster.repository.GroupTestRepository;
import com.testmaster.repository.TestRepository.TestRepository;
import com.testmasterapi.domain.group.request.GroupTestsAddRequest;
import com.testmasterapi.domain.group.GroupTestId;
import com.testmasterapi.domain.test.data.TestGroupsData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultGroupTestService implements GroupTestService {
    private final TestMapper testMapper;

    private final GroupTestRepository groupTestRepository;
    private final TestRepository testRepository;
    private final GroupRepository groupRepository;

    @Override
    public List<TestGroupsData> getAllTestsGroup(Long groupId) {
        return groupTestRepository
                .findAllTestsByGroupId(groupId)
                .stream()
                .map(testMapper::toTestGroupsData)
                .toList();
    }

    @Transactional
    @Override
    public void add(Long groupId, GroupTestsAddRequest request) {
        Long testId = request.testId();

        if (groupTestRepository.existsByTest_Id(testId)) {
            throw new ClientException("Тест уже находится в группе", HttpStatus.CONFLICT.value());
        }
        Group group = groupRepository
                .findById(groupId)
                .orElseThrow(NotFoundException::new);

        Test test = testRepository
                .findById(testId)
                .orElseThrow(NotFoundException::new);

        GroupTestId id = new GroupTestId(
                test.getId(),
                group.getId()
        );

        GroupTest data = new GroupTest();
        data.setId(id);
        data.setTest(test);
        data.setGroup(group);
        groupTestRepository.save(data);
    }

    @Override
    public void delete(Long groupId, Long testId) {
        int deleted = groupTestRepository.delete(testId, groupId);
        if (deleted == 0) {
            throw new NotFoundException();
        }
    }
}
