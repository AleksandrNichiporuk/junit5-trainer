package com.dmdev.integration.util;

import com.dmdev.entity.Gender;
import com.dmdev.entity.Role;
import com.dmdev.entity.User;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class TestEntity {

    public static final User IVAN = User.builder()
            .id(1)
            .name("Ivan")
            .birthday(LocalDate.of(1900, 1, 10))
            .email("ivan@gmail.com")
            .password("111")
            .role(Role.ADMIN)
            .gender(Gender.MALE)
            .build();
}
