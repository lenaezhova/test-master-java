package com.testmaster.model;

import com.testmaster.model.Test.Test;
import com.testmaster.model.User.User;
import com.testmasterapi.domain.testSession.TestSessionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tests_sessions")
public class TestSession extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "status")
    private TestSessionStatus status = TestSessionStatus.OPENED;

    @Column(name = "count_points")
    private Integer countPoints = 0;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;
}
