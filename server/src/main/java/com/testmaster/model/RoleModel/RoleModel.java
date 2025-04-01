package com.testmaster.model.RoleModel;

import com.testmaster.model.BaseEntity;
import com.testmaster.model.UserModel.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleModel extends BaseEntity {
    @Column(nullable = false)
    private String title;
}