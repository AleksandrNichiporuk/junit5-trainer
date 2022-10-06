package com.dmdev.mapper;

import com.dmdev.dto.UserDto;
import com.dmdev.entity.Gender;
import com.dmdev.entity.Role;
import com.dmdev.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper userMapper = UserMapper.getInstance();

    @Test
    void mapAllFieldsCorrectly() {
        User givenUser = User.builder()
                .id(5)
                .role(Role.USER)
                .gender(Gender.MALE)
                .email("test@gmail.com")
                .birthday(LocalDate.of(2002, 8, 8))
                .name("testName")
                .password("pass123")
                .build();

        UserDto actual = userMapper.map(givenUser);

        assertEquals(givenUser.getId(), actual.getId());
        assertEquals(givenUser.getRole(), actual.getRole());
        assertEquals(givenUser.getGender(), actual.getGender());
        assertEquals(givenUser.getEmail(), actual.getEmail());
        assertEquals(givenUser.getBirthday(), actual.getBirthday());
        assertEquals(givenUser.getName(), actual.getName());
    }
}