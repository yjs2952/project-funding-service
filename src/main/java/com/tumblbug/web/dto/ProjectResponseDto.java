package com.tumblbug.web.dto;

import com.tumblbug.domain.project.Project;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter @ToString
public class ProjectResponseDto {
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

    public ProjectResponseDto(Project project) {
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
