package com.testmaster.service;

import lombok.Getter;
import com.testmaster.model.TestModel.TestStatus;
import com.testmaster.model.TestModel.TestModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Service
public class Test1Service {
    private final List<TestModel> tests = new ArrayList<>();
    private long id = 1;

    {
        tests.add(new TestModel("title1", "description1"));
        tests.add(new TestModel("title2", "description2"));
        tests.add(new TestModel("title3", "description3"));
        tests.add(new TestModel("title4", "description4"));
        tests.add(new TestModel("title5", "description5"));
        tests.add(new TestModel("title6", "description6"));
    }

    public void add(TestModel test) {
        test.setId(++id);
        tests.add(test);
    }

    public void delete(Long id) {
        tests.removeIf(test -> test.getId().equals(id));
    }
}
