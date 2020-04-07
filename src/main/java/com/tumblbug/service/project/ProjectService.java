package com.tumblbug.service.project;

import com.tumblbug.domain.project.Project;
import com.tumblbug.domain.project.ProjectRepository;
import com.tumblbug.web.dto.ProjectDonateRequestDto;
import com.tumblbug.web.dto.ProjectListResponseDto;
import com.tumblbug.web.dto.ProjectResponseDto;
import com.tumblbug.web.dto.ProjectSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public String save(ProjectSaveRequestDto requestDto){
        return projectRepository.save(requestDto.toEntity()).getId().toString();
    }

    public String update(String id, ProjectSaveRequestDto requestDto){
        UUID uuid = UUID.fromString(id);
        Project project = projectRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("해당 프로젝트가 존재하지 않습니다."));
        project.update(
                requestDto.getTitle(),
                requestDto.getDescription(),
                requestDto.getStartDate(),
                requestDto.getEndDate(),
                requestDto.getTargetAmount(),
                requestDto.isFlag(),
                requestDto.getUserName(),
                requestDto.getEmail(),
                requestDto.getPhoneNumber()
        );
        return id;
    }

    public String remove(String id){
        UUID uuid = UUID.fromString(id);
        Project project = projectRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("해당 프로젝트가 존재하지 않습니다."));
        projectRepository.delete(project);
        return id;
    }

    @Transactional(readOnly = true)
    public Page<ProjectListResponseDto> findAll(Pageable pageable){
        Page<Project> page = projectRepository.findAll(pageable);
        return page.map(ProjectListResponseDto::new);
    }

    @Transactional(readOnly = true)
    public ProjectResponseDto findOne(String id) {
        UUID uuid = UUID.fromString(id);
        Project project = projectRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("해당 프로젝트가 존재하지 않습니다."));
        return new ProjectResponseDto(project);
    }

    public String donate(String id, ProjectDonateRequestDto request){
        Project project = projectRepository.findById(UUID.fromString(id)).orElseThrow(() -> new EntityNotFoundException("해당 프로젝트가 존재하지 않습니다."));
        log.debug("result : {}", project);
        project.donate(request.getAmount());
        return id;
    }
}
