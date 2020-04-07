package com.tumblbug.web.dto;

import com.tumblbug.domain.project.Project;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter @ToString
public class ProjectListResponseDto {
    private String title;
    private String userName;
    private Integer targetAmount;
    private Integer count;
    private Integer amount;
    private String status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public ProjectListResponseDto(Project project) {
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
