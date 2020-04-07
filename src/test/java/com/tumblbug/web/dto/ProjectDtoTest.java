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
        ProjectSaveRequestDto dto = ProjectSaveRequestDto.builder()
                .title("프로젝트_aS12 제목%^&")
                .description("프로젝트 aS12_설명%^&")
                .userName("jasonaS12 _%^&")
                .email("yjs2952@gmail.com")
                .phoneNumber("010-7234181-2952")
                .startDate(LocalDateTime.of(2020, 4, 3, 12, 0))
                .endDate(LocalDateTime.of(2020, 4, 10, 12, 0))
                .targetAmount(1000000000)
                .flag(true)
                .build();

        // when
        Set<ConstraintViolation<ProjectSaveRequestDto>> constraintViolations = validator.validate(dto);

        // then
        for(ConstraintViolation<ProjectSaveRequestDto> constraintViolation : constraintViolations){
            log.debug("violation error message : {}", constraintViolation.getMessage());
        }

        assertThat(constraintViolations.size(), is(4));
    }
}
