package com.tumblbug.web;

import com.tumblbug.service.project.ProjectService;
import com.tumblbug.web.dto.ProjectDonateRequestDto;
import com.tumblbug.web.dto.ProjectListResponseDto;
import com.tumblbug.web.dto.ProjectResponseDto;
import com.tumblbug.web.dto.ProjectSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/projects")
public class ProjectApiController {

    private final ProjectService projectService;

    @PostMapping
    public String create(@RequestBody @Valid ProjectSaveRequestDto requestDto) {
        log.debug("request params : {}", requestDto);
        return projectService.save(requestDto);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable String id, @RequestBody @Valid ProjectSaveRequestDto requestDto) {
        log.debug("request params : {}", requestDto);
        return projectService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        return projectService.remove(id);
    }

    @GetMapping
    public Page<ProjectListResponseDto> list(@PageableDefault(sort = {"startDate"}, direction = Sort.Direction.DESC) Pageable pageable){
        return projectService.findAll(pageable);
    }

    @GetMapping("{id}")
    public ProjectResponseDto show(@PathVariable String id){
        return projectService.findOne(id);
    }

    @PutMapping("/{id}/donate")
    public String update(@PathVariable String id, ProjectDonateRequestDto request){
        return projectService.donate(id, request);
    }
}
