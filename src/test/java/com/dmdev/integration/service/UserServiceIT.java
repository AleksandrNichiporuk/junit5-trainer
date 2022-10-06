package com.dmdev.integration.service;

import com.dmdev.dao.UserDao;
import com.dmdev.dto.CreateUserDto;
import com.dmdev.dto.UserDto;
import com.dmdev.exception.ValidationException;
import com.dmdev.integration.IntegrationTestBase;
import com.dmdev.mapper.CreateUserMapper;
import com.dmdev.mapper.UserMapper;
import com.dmdev.service.UserService;
import com.dmdev.validator.CreateUserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static com.dmdev.integration.util.TestEntity.IVAN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceIT extends IntegrationTestBase {

    private UserService userService;

    @BeforeEach
    void init() {
        userService = new UserService(
                CreateUserValidator.getInstance(),
                UserDao.getInstance(),
                CreateUserMapper.getInstance(),
                UserMapper.getInstance());
    }

    @Test
    void whenLoginSuccessfullyThenReturnDto() {
        Optional<UserDto> actualResult = userService.login(IVAN.getEmail(), IVAN.getPassword());

        assertThat(actualResult).isPresent();
        assertEquals(actualResult.get().getEmail(), IVAN.getEmail());
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForLogin")
    void whenLoginWithUncorrectEmailOrPasswordThenReturnEmpty(String email, String password) {
        Optional<UserDto> actualResult = userService.login(email, password);

        assertThat(actualResult).isEmpty();
    }

    public static Stream<Arguments> getArgumentsForLogin() {
        return Stream.of(
                Arguments.of(IVAN.getEmail(), "dummy"),
                Arguments.of("dummy", IVAN.getPassword()),
                Arguments.of("dummy", "dummy")
        );
    }


    @Test
    void whenCreateWithCorrectlyFilledFieldsThenReturnDto() {
        CreateUserDto givenUserDto = CreateUserDto.builder()
                .birthday("2000-05-07")
                .email("test@gmail.com")
                .gender("MALE")
                .name("test")
                .role("USER")
                .password("pass")
                .build();

        UserDto createdUser = userService.create(givenUserDto);

        assertNotNull(createdUser.getId());
    }

    @Test
    void whenCreateWithUncorrectFilledFieldsThenReturnValidationExeption() {
        CreateUserDto givenUserDto = CreateUserDto.builder()
                .birthday("05-07-2000")
                .email("test@gmail.com")
                .gender("male")
                .name("test")
                .role("user")
                .password("pass")
                .build();

        ValidationException validationException = assertThrows(ValidationException.class, () -> userService.create(givenUserDto));
        assertThat(validationException.getErrors()).hasSize(3);
    }
}