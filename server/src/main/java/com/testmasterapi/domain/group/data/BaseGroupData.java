package com.testmasterapi.domain.group.data;

import com.testmasterapi.domain.user.data.UserOwnerData;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseGroupData {
    private Long id;
    private String title;
}
