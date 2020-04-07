package com.tumblbug.web.dto;

import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Slf4j
public class ProjectDtoTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void dto_유효성_검사_테스트() throws Exception {
        // given
        ProjectDto.SaveRequest dto = new ProjectDto.SaveRequest();
        dto.setTitle("프로젝트_aS12 제목%^&");
        dto.setDescription("프로젝트 aS12_설명%^&");
        dto.setUserName("jasonaS12 _%^&");
        dto.setEmail("yjs2952@gmail.com");
        dto.setPhoneNumber("010-7234181-2952");
        dto.setStartDate(LocalDateTime.of(2020, 4, 3, 12, 0));
        dto.setEndDate(LocalDateTime.of(2020, 4, 10, 12, 0));
        dto.setTargetAmount(1000000000);
        dto.setFlag(true);

        // when
        Set<ConstraintViolation<ProjectDto.SaveRequest>> constraintViolations = validator.validate(dto);

        // then
        for(ConstraintViolation<ProjectDto.SaveRequest> constraintViolation : constraintViolations){
            log.debug("violation error message : {}", constraintViolation.getMessage());
        }

        assertThat(constraintViolations.size(), is(4));
    }
}
