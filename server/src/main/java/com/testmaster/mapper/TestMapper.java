package com.testmaster.mapper;

import com.testmaster.model.Test;
import com.testmasterapi.domain.test.data.TestData;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public class TestMapper {
    @Autowired
    private UserMapper userMapper;

    public TestData toTestData(Test test) {
        var testData = new TestData();
        fillGroup(testData, test);
        return testData;
    }

    private void fillGroup(TestData data, Test test) {
        data.setId(test.getId());
        data.setTitle(test.getTitle());
        data.setDescription(test.getDescription());
        data.setStatus(test.getStatus());
        data.setOwner(userMapper.toOwner(test.getOwner()));
    }
}
