package com.testmasterapi.domain.test.request;


public record TestCreateRequest(
        String title,
        String description
) {
}