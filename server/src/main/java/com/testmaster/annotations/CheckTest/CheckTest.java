package com.testmaster.annotations.CheckTest;

import com.testmasterapi.domain.test.TestStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CheckTest {
    String testId() default "";
    String questionId() default "";
    String answerTemplateId() default "";
    String answerId() default "";
    String testSessionId() default "";
    boolean checkOwner() default false;
    boolean skipCheckStatusForOwner() default false;
    TestStatus status() default TestStatus.OPENED;
}