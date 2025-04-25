package com.testmaster.mapper;

import com.testmaster.model.Test.Test;
import com.testmaster.model.User.User;
import com.testmasterapi.domain.test.data.BaseTestData;
import com.testmasterapi.domain.test.data.TestData;
import com.testmasterapi.domain.test.data.TestForSessionData;
import com.testmasterapi.domain.test.data.TestGroupsData;
import com.testmasterapi.domain.test.request.TestCreateRequest;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public class TestMapper {
    @Autowired
    private UserMapper userMapper;

    public TestData toData(Test test) {
        var data = new TestData();

        fillTest(data, test);

        data.setStatus(test.getStatus());
        data.setCreatedAt(test.getCreatedAt());
        data.setUpdatedAt(test.getUpdatedAt());
        data.setOwner(userMapper.toOwner(test.getOwner()));

        return data;
    }

    public TestGroupsData toTestGroups(Test test) {
        var data = new TestGroupsData();

        fillTest(data, test);

        return data;
    }

    public TestForSessionData toSession(Test test) {
        var data = new TestForSessionData();

        fillTest(data, test);

        return data;
    }

    public Test toEntity(TestCreateRequest request, User user) {
        var entity = new Test();
        LocalDateTime now = LocalDateTime.now();

        entity.setDescription(request.description());
        entity.setTitle(request.title());
        entity.setOwner(user);
        entity.setCreatedAt(now);

        return entity;
    }

    private void fillTest(BaseTestData data, Test test) {
        data.setId(test.getId());
        data.setTitle(test.getTitle());
        data.setDescription(test.getDescription());
        data.setDeleted(test.getDeleted());
    }
}
