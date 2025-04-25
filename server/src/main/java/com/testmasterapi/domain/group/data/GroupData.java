package com.testmasterapi.domain.group.data;

import com.testmasterapi.domain.user.data.UserOwnerData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class GroupData extends BaseGroupData {
    private UserOwnerData owner;
}
