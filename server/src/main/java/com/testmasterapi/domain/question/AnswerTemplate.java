package com.testmasterapi.domain.question;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AnswerTemplate extends AnswerVariant {
    private String description;
}
