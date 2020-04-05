package com.tumblbug.domain.project;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.LocalDateTime;

public class ProjectTest {

    @Test
    public void 프로젝트_상태값_테스트() {
        Project project1 = Project.builder()
                .startDate(LocalDateTime.of(2020, 4, 7, 12, 0, 0))
                .endDate(LocalDateTime.of(2020, 4, 10, 12, 0, 0))
                .targetAmount(50000)
                .amount(10000)
                .build();

        Project project2 = Project.builder()
                .startDate(LocalDateTime.of(2020, 4, 3, 12, 0, 0))
                .endDate(LocalDateTime.of(2020, 4, 10, 12, 0, 0))
                .targetAmount(50000)
                .amount(10000)
                .build();

        Project project3 = Project.builder()
                .startDate(LocalDateTime.of(2020, 4, 3, 12, 0, 0))
                .endDate(LocalDateTime.of(2020, 4, 5, 12, 0, 0))
                .targetAmount(50000)
                .amount(60000)
                .build();

        Project project4 = Project.builder()
                .startDate(LocalDateTime.of(2020, 4, 3, 12, 0, 0))
                .endDate(LocalDateTime.of(2020, 4, 5, 12, 0, 0))
                .targetAmount(50000)
                .amount(20000)
                .build();

        Assertions.assertThat(project1.getStatus()).isEqualTo(Status.PREPARE);
        Assertions.assertThat(project2.getStatus()).isEqualTo(Status.PROGRESS);
        Assertions.assertThat(project3.getStatus()).isEqualTo(Status.SUCCESS);
        Assertions.assertThat(project4.getStatus()).isEqualTo(Status.FAILURE);
    }
}
