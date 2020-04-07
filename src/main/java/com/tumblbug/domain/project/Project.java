package com.tumblbug.domain.project;

import com.tumblbug.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class Project extends BaseTimeEntity {

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
    public Project(String title, String description, String userName, String email, String phoneNumber, LocalDateTime startDate, LocalDateTime endDate, Integer targetAmount, Integer count, Integer amount, boolean flag, Status status) {
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
        this.status = status;
    }

    /**
     * 프로젝트 업데이트
     * @param title
     * @param description
     * @param startDate
     * @param endDate
     * @param targetAmount
     * @param flag
     */
    public void update(String title, String description, LocalDateTime startDate, LocalDateTime endDate, Integer targetAmount, boolean flag, String userName, String email, String phoneNumber) {
        this.title = title;
        this.description = description;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.targetAmount = targetAmount;
        this.flag = flag;
        this.status = findCurrentStatus();
    }

    /**
     * 후원하기
     * @param amount
     */
    public void donate(Integer amount) {
        this.amount += amount;
        this.count++;

        if (count > 100000) {
            throw new ExceedMaxCountException("최대 후원금 100,000원을 초과했습니다.");
        }

        if (amount > 100000000) {
            throw new ExceedMaxAmountException("최대 후원수 100,000,000를 초과했습니다.");
        }

        this.status = findCurrentStatus();
    }

    public Status getStatus(){
        this.status = findCurrentStatus();
        return status;
    }

    private Status findCurrentStatus(){
        return Status.findByProject(this);
    }
}
