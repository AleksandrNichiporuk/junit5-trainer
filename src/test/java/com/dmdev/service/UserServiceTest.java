package com.dmdev.service;

import com.dmdev.dao.UserDao;
import com.dmdev.dto.CreateUserDto;
import com.dmdev.dto.UserDto;
import com.dmdev.entity.User;
import com.dmdev.mapper.CreateUserMapper;
import com.dmdev.mapper.UserMapper;
import com.dmdev.validator.CreateUserValidator;
import com.dmdev.validator.ValidationResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.dmdev.integration.util.TestEntity.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private CreateUserValidator createUserValidator;
    @Mock
    private UserDao userDao;
    @Mock
    private CreateUserMapper createUserMapper;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserService userService;

    @Test
    void loginWithCallUserDaoAndUserMapper() {
        UserDto userDto = UserDto.builder()
                .id(IVAN.getId())
                .name(IVAN.getName())
                .email(IVAN.getEmail())
                .build();

        doReturn(Optional.of(IVAN)).when(userDao).findByEmailAndPassword(IVAN.getEmail(), IVAN.getPassword());
        doReturn(userDto).when(userMapper).map(IVAN);

        Optional<UserDto> actual = userService.login(IVAN.getEmail(), IVAN.getPassword());

        assertThat(actual).isPresent();
        assertEquals(IVAN.getId(), actual.get().getId());
    }

    @Test
    void createWithCallCreateUserValidatorAndMappers() {
        CreateUserDto testDto = CreateUserDto.builder()
                .name("testDto")
                .email("test@gmail.com")
                .build();

        User testEntity = User.builder()
                .name(testDto.getName())
                .email(testDto.getEmail())
                .build();

        UserDto testUserDto = UserDto.builder()
                .name(testEntity.getName())
                .email(testEntity.getEmail())
                .build();

        doReturn(new ValidationResult()).when(createUserValidator).validate(testDto);
        doReturn(testEntity).when(createUserMapper).map(testDto);
        doReturn(testUserDto).when(userMapper).map(testEntity);

        UserDto actual = userService.create(testDto);

        assertEquals(testDto.getEmail(), actual.getEmail());
        assertSame(testUserDto,actual);
    }
}