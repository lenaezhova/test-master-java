package com.testmasterapi.domain.answerVariant.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AnswerVariantPrivateData extends AnswerVariantPublicData {
    private Boolean isCorrect;
    private Integer countPoints;
}
