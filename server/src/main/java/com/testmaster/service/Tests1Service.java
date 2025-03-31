package com.testmaster.service;

import lombok.Getter;
import com.testmaster.model.Tests.TestStatus;
import com.testmaster.model.Tests.Tests;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Service
public class Tests1Service {
    private final List<Tests> tests = new ArrayList<>();
    private long id = 1;

    {
        tests.add(new Tests(id++, null, "title1", TestStatus.CLOSED, "description1"));
        tests.add(new Tests(id++, null, "title2", TestStatus.CLOSED, "description2"));
        tests.add(new Tests(id++, null, "title3", TestStatus.CLOSED, "description3"));
        tests.add(new Tests(id++, null, "title4", TestStatus.CLOSED, "description4"));
        tests.add(new Tests(id++, null, "title5", TestStatus.CLOSED, "description5"));
        tests.add(new Tests(id++, null, "title6", TestStatus.CLOSED, "description6"));
    }

    public void add(Tests test) {
        test.setId(++id);
        tests.add(test);
    }

    public void delete(Long id) {
        tests.removeIf(test -> test.getId().equals(id));
    }
}
