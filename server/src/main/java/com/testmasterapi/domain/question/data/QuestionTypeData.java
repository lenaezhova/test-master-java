package com.testmasterapi.domain.question.data;

import com.testmasterapi.domain.question.QuestionTypes;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class QuestionTypeData {
    private Long id;
    private String title;
    private Set<QuestionTypes> types;
}
