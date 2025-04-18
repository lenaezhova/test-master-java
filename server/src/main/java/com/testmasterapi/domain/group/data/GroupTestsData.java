package com.testmasterapi.domain.group.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupTestsData {
    private Long id;
    private String title;

    // додеать логику с работой групп тестов
    // тут скорее всего нужно дату теста вставлять отдельно прям
    // может быть в групппах тоже возрашать какую нибудь дату
}
