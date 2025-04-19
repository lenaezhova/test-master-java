package com.testmaster.mapper;

import com.testmaster.model.Test;
import com.testmasterapi.domain.test.data.TestData;
import com.testmasterapi.domain.test.data.TestGroupsData;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

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
        data.setOwner(userMapper.toOwner(test.getOwner()));
        return data;
    }

    public TestGroupsData toTestGroupsData(Test test) {
        TestGroupsData data = new TestGroupsData();
        data.setId(test.getId());
        data.setTitle(test.getTitle());

        return data;
    }
}
