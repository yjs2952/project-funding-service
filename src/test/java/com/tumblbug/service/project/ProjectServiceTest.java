package com.tumblbug.service.project;

import com.tumblbug.domain.project.Project;
import com.tumblbug.domain.project.ProjectRepository;
import com.tumblbug.web.dto.ProjectDto;
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
        ProjectDto.SaveRequest dto1 = new ProjectDto.SaveRequest();
        dto1.setTitle("프로젝트 제목");
        dto1.setDescription("프로젝트 설명");
        dto1.setUserName("jason");
        dto1.setEmail("yjs2952@gmail.com");
        dto1.setPhoneNumber("010-7181-2952");
        dto1.setStartDate(LocalDateTime.of(2020, 4, 3, 12, 0));
        dto1.setEndDate(LocalDateTime.of(2020, 4, 10, 12, 0));
        dto1.setTargetAmount(1000000);
        dto1.setFlag(true);

        ProjectDto.SaveRequest dto2 = new ProjectDto.SaveRequest();
        dto2.setTitle("프로젝트 제목11");
        dto2.setDescription("프로젝트 설명11");
        dto2.setUserName("jason11");
        dto2.setEmail("yjs2952@gmail.com");
        dto2.setPhoneNumber("010-7181-2952");
        dto2.setStartDate(LocalDateTime.of(2020, 4, 3, 12, 0));
        dto2.setEndDate(LocalDateTime.of(2020, 4, 10, 12, 0));
        dto2.setTargetAmount(2000000);
        dto2.setFlag(false);

        id = projectService.save(dto1);
        id2 = projectService.save(dto2);
    }

    @Test
    public void 프로젝트_등록_테스트(){
        ProjectDto.SaveRequest dto = new ProjectDto.SaveRequest();
        dto.setTitle("프로젝트 제목");
        dto.setDescription("프로젝트 설명");
        dto.setUserName("jason");
        dto.setEmail("yjs2952@gmail.com");
        dto.setPhoneNumber("010-7181-2952");
        dto.setStartDate(LocalDateTime.of(2020, 4, 3, 12, 0));
        dto.setEndDate(LocalDateTime.of(2020, 4, 10, 12, 0));
        dto.setTargetAmount(1000000);
        dto.setFlag(true);

        String id = projectService.save(dto);

        Assertions.assertThat(id).isNotEmpty();
    }

    @Test
    @Rollback(false)
    @Transactional
    public void 프로젝트_수정_테스트(){

        ProjectDto.SaveRequest request = new ProjectDto.SaveRequest();
        request.setEmail("yjs2952@naver.com");
        request.setFlag(true);
        request.setTitle("프로젝트 제목111");
        request.setStartDate(LocalDateTime.of(2020, 4, 1, 12, 0));
        request.setEndDate(LocalDateTime.of(2020, 4, 30, 12, 0));

        projectService.update(id, request);

        Project findProject = projectRepository.getOne(UUID.fromString(id));

        Assertions.assertThat(request.getTitle()).isEqualTo(findProject.getTitle());
    }

    @Test
    public void 프로젝트_조회(){

        ProjectDto.Response one = projectService.findOne(id);
        Page<ProjectDto.ListResponse> page = projectService.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "targetAmount")));

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
        ProjectDto.DonateRequest request = new ProjectDto.DonateRequest();
        request.setAmount(10000);

        ProjectDto.Response one = projectService.findOne(id);
        Integer amount = one.getAmount();
        Integer count = one.getCount();

        projectService.donate(id, request);

        Project project = projectRepository.getOne(UUID.fromString(id));

        Assertions.assertThat(amount + request.getAmount()).isEqualTo(project.getAmount());
        Assertions.assertThat(count + 1).isEqualTo(project.getCount());
    }
}
