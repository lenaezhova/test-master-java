package com.testmaster.model.GroupModel;

import com.testmaster.model.BaseEntity;
import com.testmaster.model.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "groups")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupModel extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserModel owner;

    @Column(nullable = false)
    private String title;
}