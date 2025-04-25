package com.testmasterapi.domain.test.event;

import com.testmaster.model.Test.Test;

public record TestEvent(
        Test test,
        TestEventsType eventsType
) {
}