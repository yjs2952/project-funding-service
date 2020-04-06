package com.tumblbug.web;

import com.tumblbug.service.project.ProjectService;
import com.tumblbug.web.dto.ProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/projects")
public class ProjectApiController {

    private final ProjectService projectService;

    @PostMapping
    public UUID save(@RequestBody @Valid ProjectDto.SaveRequest requestDto) {
        return projectService.save(requestDto);
    }
}
