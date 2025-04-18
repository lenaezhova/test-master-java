package com.testmasterapi.domain.group.data;

import com.testmasterapi.domain.user.data.UserOwnerData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupData {
    private Long id;
    private String title;
    private UserOwnerData owner;
}
