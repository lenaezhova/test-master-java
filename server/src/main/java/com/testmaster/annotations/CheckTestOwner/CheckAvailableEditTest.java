package com.testmaster.annotations.CheckTestOwner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CheckAvailableEditTest {
    String testId() default "";
    String questionId() default "";
    boolean checkTestIsOpen() default true;
}