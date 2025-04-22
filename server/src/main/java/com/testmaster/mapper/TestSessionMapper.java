package com.testmaster.mapper;

import com.testmaster.model.Group.Group;
import com.testmaster.model.Test;
import com.testmaster.model.TestSession;
import com.testmaster.model.User;
import com.testmasterapi.domain.group.data.GroupData;
import com.testmasterapi.domain.group.data.GroupsUserData;
import com.testmasterapi.domain.group.request.GroupCreateRequest;
import com.testmasterapi.domain.testSession.TestSessionStatus;
import com.testmasterapi.domain.testSession.data.TestSessionData;
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
        data.setTest(testMapper.toSessionData(testSession.getTest()));
        data.setClosedAt(testSession.getClosedAt());
        data.setCreatedAt(testSession.getCreatedAt());

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
