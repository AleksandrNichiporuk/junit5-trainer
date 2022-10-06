package com.dmdev.mapper;

import com.dmdev.dto.CreateUserDto;
import com.dmdev.entity.Gender;
import com.dmdev.entity.Role;
import com.dmdev.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CreateUserMapperTest {

    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();

    @Test
    void mapAllFieldsCorrectly() {
        CreateUserDto givenUserDto = CreateUserDto.builder()
                .password("pass")
                .role(Role.ADMIN.name())
                .name("test")
                .email("test@gmail.com")
                .gender(Gender.FEMALE.name())
                .birthday("2001-09-01")
                .build();

        User actual = createUserMapper.map(givenUserDto);

        assertEquals(givenUserDto.getPassword(),actual.getPassword());
        assertEquals(Role.valueOf(givenUserDto.getRole()),actual.getRole());
        assertEquals(givenUserDto.getName(),actual.getName());
        assertEquals(givenUserDto.getEmail(),actual.getEmail());
        assertEquals(Gender.valueOf(givenUserDto.getGender()),actual.getGender());
        assertEquals(LocalDate.of(2001,9,1),actual.getBirthday());
    }
}