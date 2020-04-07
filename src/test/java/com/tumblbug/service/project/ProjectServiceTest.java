package com.tumblbug.service.project;

import com.tumblbug.domain.project.Project;
import com.tumblbug.domain.project.ProjectRepository;
import com.tumblbug.web.dto.ProjectDonateRequestDto;
import com.tumblbug.web.dto.ProjectListResponseDto;
import com.tumblbug.web.dto.ProjectResponseDto;
import com.tumblbug.web.dto.ProjectSaveRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    private String id;
    private String id2;

    @Before
    public void setUp(){

        ProjectSaveRequestDto dto1 = ProjectSaveRequestDto.builder()
                .title("프로젝트 제목")
                .description("프로젝트 설명")
                .userName("jason")
                .email("yjs2952@gmail.com")
                .phoneNumber("010-7181-2952")
                .startDate(LocalDateTime.of(2020, 4, 3, 12, 0))
                .endDate(LocalDateTime.of(2020, 4, 10, 12, 0))
                .targetAmount(1000000)
                .flag(true)
                .build();

        ProjectSaveRequestDto dto2 = ProjectSaveRequestDto.builder()
                .title("프로젝트 제목11")
                .description("프로젝트 설명11")
                .userName("jason11")
                .email("yjs2952@gmail.com")
                .phoneNumber("010-7181-2952")
                .startDate(LocalDateTime.of(2020, 4, 3, 12, 0))
                .endDate(LocalDateTime.of(2020, 4, 10, 12, 0))
                .targetAmount(1000000)
                .flag(true)
                .build();

        id = projectService.save(dto1);
        id2 = projectService.save(dto2);
    }

    @Test
    public void 프로젝트_등록_테스트(){

        ProjectSaveRequestDto dto = ProjectSaveRequestDto.builder()
                .title("프로젝트 제목")
                .description("프로젝트 설명")
                .userName("jason")
                .email("yjs2952@gmail.com")
                .phoneNumber("010-7181-2952")
                .startDate(LocalDateTime.of(2020, 4, 3, 12, 0))
                .endDate(LocalDateTime.of(2020, 4, 10, 12, 0))
                .targetAmount(1000000)
                .flag(true)
                .build();

        String id = projectService.save(dto);

        Assertions.assertThat(id).isNotEmpty();
    }

    @Test
    @Rollback(false)
    @Transactional
    public void 프로젝트_수정_테스트(){

        ProjectSaveRequestDto request = ProjectSaveRequestDto.builder()
                .title("프로젝트 제목111")
                .description("프로젝트 설명111")
                .userName("jason")
                .email("yjs2952@naver.com")
                .phoneNumber("010-7181-2952")
                .startDate(LocalDateTime.of(2020, 4, 3, 12, 0))
                .endDate(LocalDateTime.of(2020, 4, 10, 12, 0))
                .targetAmount(3000000)
                .flag(true)
                .build();

        projectService.update(id, request);

        Project findProject = projectRepository.getOne(UUID.fromString(id));

        Assertions.assertThat(request.getTitle()).isEqualTo(findProject.getTitle());
    }

    @Test
    public void 프로젝트_조회(){

        ProjectResponseDto one = projectService.findOne(id);
        Page<ProjectListResponseDto> page = projectService.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "targetAmount")));

        Assertions.assertThat(one).isNotNull();
        Assertions.assertThat(page.getTotalElements()).isEqualTo(2);

        log.debug("one result : {}", one);
        log.debug("page result : {}", page.getContent());
    }
    
    @Test
    public void 프로젝트_삭제(){

        String removeId = projectService.remove(id);

        Optional<Project> optional = projectRepository.findById(UUID.fromString(removeId));

        Assertions.assertThat(optional.isPresent()).isFalse();
    }

    @Test
    @Transactional
    public void 프로젝트_후원(){
        ProjectDonateRequestDto request = new ProjectDonateRequestDto();
        request.setAmount(10000);

        ProjectResponseDto one = projectService.findOne(id);
        Integer amount = one.getAmount();
        Integer count = one.getCount();

        projectService.donate(id, request);

        Project project = projectRepository.getOne(UUID.fromString(id));

        Assertions.assertThat(amount + request.getAmount()).isEqualTo(project.getAmount());
        Assertions.assertThat(count + 1).isEqualTo(project.getCount());
    }
}
