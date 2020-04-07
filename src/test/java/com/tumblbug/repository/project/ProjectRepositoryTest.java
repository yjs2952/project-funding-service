package com.tumblbug.repository.project;

import com.tumblbug.domain.project.Project;
import com.tumblbug.domain.project.ProjectRepository;
import com.tumblbug.web.dto.ProjectDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    private ProjectDto.SaveRequest dto;

    @Before
    public void setUp(){
        dto = new ProjectDto.SaveRequest();
        dto.setTitle("프로젝트 제목");
        dto.setDescription("프로젝트 설명");
        dto.setUserName("jason");
        dto.setEmail("yjs2952@gmail.com");
        dto.setPhoneNumber("010-7181-2952");
        dto.setStartDate(LocalDateTime.of(2020, 4, 3, 12, 0));
        dto.setEndDate(LocalDateTime.of(2020, 4, 10, 12, 0));
        dto.setTargetAmount(1000000);
        dto.setFlag(true);

        projectRepository.save(dto.toEntity());
    }


    @Rollback(false)
    @Test
    public void 프로젝트_등록_테스트(){

        // given
        dto = new ProjectDto.SaveRequest();
        dto.setTitle("프로젝트 제목1");
        dto.setDescription("프로젝트 설명2");
        dto.setUserName("jason3");
        dto.setEmail("yjs2952@gmail.com");
        dto.setPhoneNumber("010-7181-2952");
        dto.setStartDate(LocalDateTime.of(2020, 4, 3, 12, 0));
        dto.setEndDate(LocalDateTime.of(2020, 4, 10, 12, 0));
        dto.setTargetAmount(1000000);
        dto.setFlag(true);

        // when
        Project savedProject = projectRepository.save(dto.toEntity());

        // then
        Assertions.assertThat(dto.toEntity().getStatus()).isEqualTo(savedProject.getStatus());
        Assertions.assertThat(dto.getTitle()).isEqualTo(savedProject.getTitle());
    }

    @Rollback(false)
    @Test
    public void 프로젝트_업데이트(){

        // given
        Project project = projectRepository.findAll().get(0);

        // when
        project.update(
                "제목333",
                "설명설명설명",
                LocalDateTime.of(2020,4,1,12,0),
                LocalDateTime.of(2020,4,1,20,0),
                10000,
                false,
                "jason",
                "yjs2952@naver.com",
                "010-7181-2952"
        );

        // then
        Assertions.assertThat(project.getTitle()).isEqualTo("제목333");
    }

    @Test
    public void 프로젝트_전체_조회_테스트(){

        Project project = Project.builder()
                .title("title")
                .description("blah blah blah")
                .userName("jason")
                .email("yjs2952@gmail.com")
                .phoneNumber("010-7181-2952")
                .startDate(LocalDateTime.of(2020, 4, 1, 12, 0))
                .endDate(LocalDateTime.of(2020, 4, 30, 12, 0))
                .targetAmount(1000000)
                .amount(0)
                .count(0)
                .flag(true)
                .build();

        Project savedProject = projectRepository.save(project);

        // given
        UUID uuid = savedProject.getId();
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Optional<Project> selectProject = projectRepository.findById(uuid);
        Page<Project> projects = projectRepository.findAll(pageRequest);

        // then
        Assertions.assertThat(selectProject.isPresent()).isTrue();
        Assertions.assertThat(projects.getTotalElements()).isEqualTo(1);

        log.debug("result : {}", projects.getContent().get(0).getId());
    }

    @Rollback(false)
    @Test
    public void 프로젝트_삭제_테스트(){
        // given
        Project project = projectRepository.findAll().get(0);

        UUID uuid = project.getId();

        // when
        projectRepository.delete(project);

        // then
        Assertions.assertThat(projectRepository.findById(uuid).isPresent()).isFalse();
    }
}
