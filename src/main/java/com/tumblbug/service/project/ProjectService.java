package com.tumblbug.service.project;

import com.tumblbug.domain.project.Project;
import com.tumblbug.domain.project.ProjectRepository;
import com.tumblbug.web.dto.ProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional
    public String save(ProjectDto.SaveRequest requestDto){
        return projectRepository.save(requestDto.toEntity()).getId().toString();
    }

    @Transactional
    public String update(String id, ProjectDto.SaveRequest requestDto){
        UUID uuid = UUID.fromString(id);
        Project project = projectRepository.findById(uuid).orElseThrow(() -> new NotFoundException("해당 프로젝트가 존재하지 않습니다."));
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

    @Transactional
    public String remove(String id){
        UUID uuid = UUID.fromString(id);
        Project project = projectRepository.findById(uuid).orElseThrow(() -> new NotFoundException("해당 프로젝트가 존재하지 않습니다."));
        projectRepository.delete(project);
        return id;
    }

    @Transactional(readOnly = true)
    public Page<ProjectDto.ListResponse> findAll(Pageable pageable){
        Page<Project> page = projectRepository.findAll(pageable);
        return page.map(ProjectDto.ListResponse::new);
    }

    @Transactional(readOnly = true)
    public ProjectDto.Response findOne(String id) {
        UUID uuid = UUID.fromString(id);
        Project project = projectRepository.findById(uuid).orElseThrow(() -> new NotFoundException("해당 프로젝트가 존재하지 않습니다."));
        return new ProjectDto.Response(project);
    }

    @Transactional
    public String donate(String id, ProjectDto.DonateRequest request){
        Project project = projectRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException("해당 프로젝트가 존재하지 않습니다."));
        project.donate(request.getAmount());
        return id;
    }
}
