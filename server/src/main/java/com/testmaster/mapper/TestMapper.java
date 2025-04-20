package com.testmaster.mapper;

import com.testmaster.model.Test;
import com.testmaster.model.User;
import com.testmasterapi.domain.test.data.TestData;
import com.testmasterapi.domain.test.data.TestGroupsData;
import com.testmasterapi.domain.test.request.TestCreateRequest;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public class TestMapper {
    @Autowired
    private UserMapper userMapper;

    public TestData toTestData(Test test) {
        var data = new TestData();

        data.setId(test.getId());
        data.setTitle(test.getTitle());
        data.setDescription(test.getDescription());
        data.setStatus(test.getStatus());
        data.setCreatedAt(test.getCreatedAt());
        data.setUpdatedAt(test.getUpdatedAt());
        data.setOwner(userMapper.toOwner(test.getOwner()));
        return data;
    }

    public TestGroupsData toTestGroupsData(Test test) {
        var data = new TestGroupsData();
        data.setId(test.getId());
        data.setTitle(test.getTitle());
        return data;
    }

    public Test toEntity(TestCreateRequest request, User user) {
        LocalDateTime now = LocalDateTime.now();
        var entity = new Test();

        entity.setDescription(request.description());
        entity.setTitle(request.title());
        entity.setOwner(user);
        entity.setCreatedAt(now);

        return entity;
    }
}
