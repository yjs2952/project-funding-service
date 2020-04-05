package com.tumblbug.domain.project;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class Project {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(length = 50)
    private String title;

    private String description;

    @Column(length = 20)
    private String userName;

    private String email;

    private String phoneNumber;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Integer targetAmount;

    private Integer count;

    private Integer amount;

    private boolean flag;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Builder
    public Project(String title, String description, String userName, String email, String phoneNumber, LocalDateTime startDate, LocalDateTime endDate, Integer targetAmount, Integer count, Integer amount, boolean flag) {
        this.title = title;
        this.description = description;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.targetAmount = targetAmount;
        this.count = count;
        this.amount = amount;
        this.flag = flag;
        this.status = getStatusByProject();
    }

    public void update(String title, String description, LocalDateTime startDate, LocalDateTime endDate, Integer targetAmount, Integer amount, boolean flag) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.targetAmount = targetAmount;
        this.amount = amount;
        this.flag = flag;
        this.status = getStatusByProject();
    }

    private Status getStatusByProject(){
        return Status.findByProject(this);
    }
}
