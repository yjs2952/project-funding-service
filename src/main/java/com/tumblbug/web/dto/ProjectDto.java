package com.tumblbug.web.dto;

import com.tumblbug.domain.project.Project;
import com.tumblbug.domain.project.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class ProjectDto {

    @Getter @Setter @ToString
    public static class SaveRequest {

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

    @Getter @Setter @ToString
    public static class ListResponse {
        private String title;
        private String userName;
        private Integer targetAmount;
        private Integer count;
        private Integer amount;
        private String status;
        private LocalDateTime startDate;
        private LocalDateTime endDate;

        public ListResponse(Project project) {
            this.title = project.getTitle();
            this.userName = project.getUserName();
            this.targetAmount = project.getTargetAmount();
            this.count = project.getCount();
            this.amount = project.getAmount();
            this.status = project.getStatus().name();
            this.startDate = project.getStartDate();
            this.endDate = project.getEndDate();
        }
    }

    @Getter @Setter @ToString
    public static class Response {
        private String title;
        private String description;
        private String userName;
        private String email;
        private String phoneNumber;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Integer targetAmount;
        private Integer count;
        private Integer amount;
        private boolean flag;
        private String status;

        public Response(Project project) {
            this.title = project.getTitle();
            this.description = project.getDescription();
            this.userName = project.getUserName();
            this.email = project.getEmail();
            this.phoneNumber = project.getPhoneNumber();
            this.startDate = project.getStartDate();
            this.endDate = project.getEndDate();
            this.targetAmount = project.getTargetAmount();
            this.count = project.getCount();
            this.amount = project.getAmount();
            this.flag = project.isFlag();
            this.status = project.getStatus().name();
        }
    }

    @Getter @Setter @ToString
    public static class DonateRequest {
        Integer amount;
    }
}
