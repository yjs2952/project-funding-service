package com.tumblbug.domain.project;

import com.tumblbug.web.dto.DonationDto;
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

    private Predicate<DonationDto> condition;

    Status(Predicate<DonationDto> condition) {
        this.condition = condition;
    }

    public boolean check(DonationDto dto) {
        return condition.test(dto);
    }

    public static Status findCurrentStatus(DonationDto dto) {
        return Arrays.stream(Status.values())
                .filter(status -> status.check(dto))
                .findAny()
                .orElseThrow(RuntimeException::new);
    }
}
