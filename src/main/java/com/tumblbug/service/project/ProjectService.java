package com.tumblbug.service.project;

import com.tumblbug.domain.project.ProjectRepository;
import com.tumblbug.web.dto.ProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public UUID save(ProjectDto.SaveRequest requestDto){
        return projectRepository.save(requestDto.toEntity()).getId();
    }
}
