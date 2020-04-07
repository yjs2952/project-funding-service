package com.tumblbug.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tumblbug.web.dto.ProjectDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ProjectApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void 프로젝트_등록_요청_파라미터_유효성_검사() throws Exception {

        // given
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

        // when
//        when(projectService.save(dto)).thenReturn("프로젝트 제목1");
        // then
        mvc.perform(
                post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(dto))
        ).andExpect(status().isOk())
        .andDo(print());
    }
}
