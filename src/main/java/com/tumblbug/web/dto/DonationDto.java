package com.tumblbug.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter @ToString
public class DonationDto {
    private Integer amount;
    private Integer targetAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Builder
    public DonationDto(Integer amount, Integer targetAmount, LocalDateTime startDate, LocalDateTime endDate) {
        this.amount = amount;
        this.targetAmount = targetAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
