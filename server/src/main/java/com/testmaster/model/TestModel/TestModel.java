package com.testmaster.model.TestModel;

import com.testmaster.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.testmaster.model.UserModel.UserModel;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tests")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestModel extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserModel owner;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TestStatus status = TestStatus.CLOSED;

    private String description;

    public TestModel( String title, String description ) {
        this.title = title;
        this.description = description;
    }
}