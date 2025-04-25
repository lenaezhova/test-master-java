package com.testmasterapi.domain.question;

import java.util.List;

public enum QuestionTypes {
    TEXT,
    SINGLE,
    MULTIPLE;

    public static List<QuestionTypes> choiceTypes() {
        return List.of(SINGLE, MULTIPLE);
    }
}
