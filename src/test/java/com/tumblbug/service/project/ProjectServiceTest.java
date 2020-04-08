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
import org.springframework.test.context.junit4.SpringRunner;

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

        // given
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

        // when
        String id = projectService.save(dto);

        // then
        Assertions.assertThat(id).isNotEmpty();
    }

    @Test
    public void 프로qq젝트_수정_테스트(){

        // given
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

        // when
        projectService.update(id, request);

        ProjectResponseDto dto = projectService.findOne(id);

        // then
        Assertions.assertThat(request.getTitle()).isEqualTo(dto.getTitle());
    }

    @Test
    public void 프로젝트_조회(){

        // when
        ProjectResponseDto one = projectService.findOne(id);
        Page<ProjectListResponseDto> page = projectService.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "targetAmount")));

        // then
        Assertions.assertThat(one).isNotNull();
        Assertions.assertThat(page.getTotalElements()).isEqualTo(2);

        log.debug("one result : {}", one);
        log.debug("page result : {}", page.getContent());
    }
    
    @Test
    public void 프로젝트_삭제(){

        // when
        String removeId = projectService.remove(id);

        Optional<Project> optional = projectRepository.findById(UUID.fromString(removeId));

        // then
        Assertions.assertThat(optional.isPresent()).isFalse();
    }

    @Test
    public void 프로젝트_후원(){

        // given
        ProjectDonateRequestDto request = new ProjectDonateRequestDto();
        request.setAmount(10000);

        ProjectResponseDto one = projectService.findOne(id);
        Integer amount = one.getAmount();
        Integer count = one.getCount();

        // when
        projectService.donate(id, request);

        ProjectResponseDto dto = projectService.findOne(id);

        // then
        Assertions.assertThat(amount + request.getAmount()).isEqualTo(dto.getAmount());
        Assertions.assertThat(count + 1).isEqualTo(dto.getCount());
    }
}
