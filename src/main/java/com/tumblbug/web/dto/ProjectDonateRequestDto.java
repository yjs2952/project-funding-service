package com.tumblbug.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter @Setter @ToString
public class ProjectDonateRequestDto {

    @NotNull(message = "후원액을 입력해 주세요.")
    @PositiveOrZero(message = "음수 값을 입력할 수 없습니다.")
    @Max(value = 100000000, message = "최대 100,000,000 원을 넘을 수 없습니다.")
    private Integer amount;
}
