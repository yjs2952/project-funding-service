package com.tumblbug.web.dto;

import com.tumblbug.domain.project.Project;
import com.tumblbug.domain.project.Status;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter @ToString
public class ProjectSaveRequestDto {
    @NotBlank(message = "제목을 입력해 주세요.")
    @Size(max = 50, message = "50자 이내로 입력해주세요.")
    @Pattern(regexp = "^[0-9a-zA-Z가-힣\\s]*$", message = "한글, 숫자, 영문만 입력가능합니다.")
    private String title;

    @NotBlank(message = "설명을 입력해 주세요.")
    @Size(max = 255, message = "255자 이내로 입력해 주세요.")
    @Pattern(regexp = "^[0-9a-zA-Z가-힣\\s\\W_]*$", message = "한글, 숫자, 영문, 특수문자만 입력가능합니다.")
    private String description;

    @NotBlank(message = "창작자 이름을 입력해 주세요.")
    @Size(max = 20, message = "20자 이내로 입력해 주세요.")
    @Pattern(regexp = "^[0-9a-zA-Z가-힣_]*$", message = "한글, 숫자, 영문, 특수문자(_)만 입력가능합니다.")
    private String userName;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "메일의 양식에 맞게 입력해주세요.")
    private String email;

    @NotBlank(message = "휴대전화번호를 입력해주세요.")
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "휴대전화 양식에 맞게 입력해주세요.")
    private String phoneNumber;

    @NotNull(message = "시작일을 입력해 주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @NotNull(message = "마감일을 입력해 주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @NotNull(message = "목표액을 입력해 주세요.")
    @PositiveOrZero(message = "음수 값을 입력할 수 없습니다.")
    @Max(value = 100000000, message = "최대 100,000,000 원을 넘을 수 없습니다.")
    private Integer targetAmount;

    private boolean flag = true;

    @Builder
    public ProjectSaveRequestDto(String title, String description, String userName,  String phoneNumber,  LocalDateTime startDate, LocalDateTime endDate, String email, Integer targetAmount, boolean flag) {
        this.title = title;
        this.description = description;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.targetAmount = targetAmount;
        this.flag = flag;
    }

    public Project toEntity() {
        return Project.builder()
                .title(title)
                .description(description)
                .userName(userName)
                .email(email)
                .phoneNumber(phoneNumber)
                .startDate(startDate)
                .endDate(endDate)
                .targetAmount(targetAmount)
                .count(0)
                .amount(0)
                .flag(flag)
                .status(Status.PREPARE)
                .build();
    }
}
