package com.tumblbug.domain.project;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.function.Predicate;

@Getter
public enum Status {
    PREPARE(p -> LocalDateTime.now().isBefore(p.getStartDate())),
    PROGRESS(p -> LocalDateTime.now().isAfter(p.getStartDate()) && LocalDateTime.now().isBefore(p.getEndDate())),
    SUCCESS(p -> LocalDateTime.now().isAfter(p.getEndDate()) && p.getAmount() >= p.getTargetAmount()),
    FAILURE(p -> LocalDateTime.now().isAfter(p.getEndDate()) && p.getAmount() < p.getTargetAmount());

    private Predicate<Project> condition;

    Status(Predicate<Project> condition) {
        this.condition = condition;
    }

    public boolean check(Project project) {
        return condition.test(project);
    }

    public static Status findByProject(Project project) {
        return Arrays.stream(Status.values())
                .filter(status -> status.check(project))
                .findAny()
                .orElseThrow(RuntimeException::new);
    }
}
