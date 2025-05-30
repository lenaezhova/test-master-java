package com.testmaster.mapper;

import com.testmaster.model.Test.Test;
import com.testmaster.model.TestSession;
import com.testmaster.model.User.User;
import com.testmasterapi.domain.testSession.TestSessionStatus;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.testSession.data.TestSessionResultData;
import com.testmasterapi.domain.testSession.request.TestSessionCreateRequest;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public class TestSessionMapper {
    @Autowired
    private TestMapper testMapper;

    public TestSessionData toData(TestSession testSession) {
        var data = new TestSessionData();

        data.setId(testSession.getId());
        data.setStatus(testSession.getStatus());
        data.setCountPoints(testSession.getCountPoints());
        data.setTest(testMapper.toSession(testSession.getTest()));
        data.setClosedAt(testSession.getClosedAt());
        data.setCreatedAt(testSession.getCreatedAt());

        return data;
    }

    public TestSessionResultData toResult(TestSession testSession) {
        var data = new TestSessionResultData();

        data.setId(testSession.getId());
        data.setCountPoints(testSession.getCountPoints());
        data.setCreatedAt(testSession.getCreatedAt());
        data.setClosedAt(testSession.getClosedAt());

        return data;
    }

    public TestSession toEntity(TestSessionCreateRequest request, Test test, User user) {
        var entity = new TestSession();
        LocalDateTime now = LocalDateTime.now();

        entity.setUser(user);
        entity.setTest(test);
        entity.setCreatedAt(now);
        entity.setStatus(TestSessionStatus.OPENED);

        return entity;
    }
}
