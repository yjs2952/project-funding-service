package com.tumblbug.web;

import com.tumblbug.service.project.ProjectService;
import com.tumblbug.web.dto.ProjectDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/projects")
public class ProjectApiController {

    private final ProjectService projectService;

    @PostMapping
    public String save(@RequestBody @Valid ProjectDto.SaveRequest requestDto) {
        log.debug("request params : {}", requestDto);
        return projectService.save(requestDto);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable String id, @RequestBody @Valid ProjectDto.SaveRequest requestDto) {
        log.debug("request params : {}", requestDto);
        return projectService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public String remove(@PathVariable String id) {
        return projectService.remove(id);
    }

    @GetMapping
    public Page<ProjectDto.ListResponse> findAll(Pageable pageable){
        return projectService.findAll(pageable);
    }

    @GetMapping("{id}")
    public ProjectDto.Response findOne(@PathVariable String id){
        return projectService.findOne(id);
    }

    @PutMapping("/{id}/donate")
    public String donate(@PathVariable String id, ProjectDto.DonateRequest request){
        return projectService.donate(id, request);
    }
}
