package com.tumblbug.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tumblbug.domain.project.Project;
import com.tumblbug.domain.project.ProjectRepository;
import com.tumblbug.web.dto.ProjectDonateRequestDto;
import com.tumblbug.web.dto.ProjectSaveRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ProjectApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ObjectMapper mapper;

    private String uuid;

    @Before
    public void setUp() {
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
        Project project = projectRepository.save(dto.toEntity());
        uuid = project.getId().toString();

        log.debug("uuid : {}", uuid);
        log.debug("amount : {}", project.getAmount());
        log.debug("count : {}", project.getCount());
    }

    @Test
    public void 프로젝트_등록_요청_파라미터_유효성_검사() throws Exception {

        // given
        ProjectSaveRequestDto dto = ProjectSaveRequestDto.builder()
                .title("프로젝트 제목!@#")
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

        // then
        mvc.perform(
                post("/api/projects/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(dto))
        ).andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void 프로젝트_수정_테스트() throws Exception {

        // given
        ProjectSaveRequestDto dto = ProjectSaveRequestDto.builder()
                .title("프로젝트 제목")
                .description("프로젝트 설명22222")
                .userName("jason")
                .email("yjs2952@gmail.com")
                .phoneNumber("010-7181-2952")
                .startDate(LocalDateTime.of(2020, 4, 3, 12, 0))
                .endDate(LocalDateTime.of(2020, 4, 10, 12, 0))
                .targetAmount(1000000)
                .flag(true)
                .build();

        Project project = projectRepository.findAll().get(0);

        String uuid = project.getId().toString();

        // then
        mvc.perform(
                put("/api/projects/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(dto))
        ).andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void 프로젝트_삭제_테스트() throws Exception {

        // given
        Project project = projectRepository.findAll().get(0);

        String uuid = project.getId().toString();

        // then
        mvc.perform(delete("/api/projects/" + uuid))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void 프로젝트_조회_테스트() throws Exception {
        mvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andDo(print()).andReturn();

        mvc.perform(get("/api/projects/" + uuid))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void 프로젝트_후원_테스트() throws Exception{

        ProjectDonateRequestDto dto = new ProjectDonateRequestDto();
        dto.setAmount(10000);

        mvc.perform(
                put("/api/projects/" + uuid + "/donate")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(dto))
        ).andExpect(status().isOk())
                .andDo(print());
    }
}
