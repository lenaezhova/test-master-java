package com.testmaster.model;

import com.testmasterapi.domain.test.TestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
}