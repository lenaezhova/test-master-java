package model.Tests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tests {
    private Long id;
    private Long ownerId;
    private String title;
    private TestStatus status;
    private String description;
}