package com.dmdev.validator;

import com.dmdev.dto.CreateUserDto;
import com.dmdev.entity.Gender;
import com.dmdev.entity.Role;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class CreateUserValidatorTest {

    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();

    @Test
    void whenAllFieldsCorrectThanValidateWithoutErrors() {
        CreateUserDto givenDto = CreateUserDto.builder()
                .name("testName")
                .password("testPass")
                .email("test@gmail.com")
                .gender(Gender.FEMALE.name())
                .birthday("2002-06-07")
                .role(Role.USER.name())
                .build();

        ValidationResult validationResult = createUserValidator.validate(givenDto);

        assertThat(validationResult.getErrors()).isEmpty();
    }

    @Test
    void whenFieldsUncorrectThanValidateWithErrors() {
        CreateUserDto givenDto = CreateUserDto.builder()
                .name("testName")
                .password("testPass")
                .email("test@gmail.com")
                .gender("dummy")
                .birthday("2002-06-07")
                .role(Role.USER.name())
                .build();

        ValidationResult validationResult = createUserValidator.validate(givenDto);

        assertThat(validationResult.getErrors()).hasSize(1);
        assertThat(validationResult.getErrors().stream().map(Error::getMessage).collect(Collectors.toList())).isEqualTo(List.of("Gender is invalid"));
    }
}