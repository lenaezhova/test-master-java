package com.testmasterapi.domain.testSession.event;

import com.testmaster.model.TestSession;

public record TestSessionEvent(
        TestSession testSession,
        TestSessionEventsType eventsType
) {
}