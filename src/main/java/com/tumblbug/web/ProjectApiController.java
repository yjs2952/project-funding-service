package com.tumblbug.web;

import com.tumblbug.service.project.ProjectService;
import com.tumblbug.web.dto.ProjectDonateRequestDto;
import com.tumblbug.web.dto.ProjectListResponseDto;
import com.tumblbug.web.dto.ProjectResponseDto;
import com.tumblbug.web.dto.ProjectSaveRequestDto;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "프로젝트 등록")
    @PostMapping
    public String create(@RequestBody @Valid ProjectSaveRequestDto requestDto) {
        log.debug("request params : {}", requestDto);
        return projectService.save(requestDto);
    }

    @ApiOperation(value = "프로젝트 수정")
    @PutMapping("/{id}")
    public String update(@PathVariable String id, @RequestBody @Valid ProjectSaveRequestDto requestDto) {
        log.debug("request params : {}", requestDto);
        return projectService.update(id, requestDto);
    }

    @ApiOperation(value = "프로젝트 삭제")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        return projectService.remove(id);
    }

    @ApiOperation(value = "프로젝트 목록 조회")
    @GetMapping
    public Page<ProjectListResponseDto> list(@PageableDefault(sort = {"startDate"}, direction = Sort.Direction.DESC) Pageable pageable){
        return projectService.findAll(pageable);
    }

    @ApiOperation(value = "프로젝트 조회")
    @GetMapping("/{id}")
    public ProjectResponseDto show(@PathVariable String id){
        return projectService.findOne(id);
    }

    @ApiOperation(value = "프로젝트 후원")
    @PutMapping("/{id}/donate")
    public String update(@PathVariable String id, @RequestBody ProjectDonateRequestDto request){
        log.debug("request params : {}", request);
        return projectService.donate(id, request);
    }
}
