package com.dmdev.integration.dao;

import com.dmdev.dao.UserDao;
import com.dmdev.entity.Gender;
import com.dmdev.entity.Role;
import com.dmdev.entity.User;
import com.dmdev.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.dmdev.integration.util.TestEntity.IVAN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserDaoIT extends IntegrationTestBase {

    private final UserDao userDao = UserDao.getInstance();

    @Test
    void whenFindAllThenReturnListEntities() {
        List<User> users = userDao.findAll();
        assertThat(users).hasSize(5);
    }

    @Test
    void whenFindByIdThenReturnExistingEntity() {
        Integer givenId = 2;

        Optional<User> actual = userDao.findById(givenId);

        assertThat(actual).isPresent();
    }

    @Test
    void whenFindByIdUnexistingEntityThenReturnEmpty() {
        Integer givenId = 10;

        Optional<User> actual = userDao.findById(givenId);

        assertThat(actual).isEmpty();
    }

    @Test
    void whenSaveThenReturnNewEntity() {
        User givenUser = User.builder()
                .birthday(LocalDate.of(2000, 5, 7))
                .email("test@gmail.com")
                .gender(Gender.FEMALE)
                .name("Test")
                .password("159")
                .role(Role.USER)
                .build();

        userDao.save(givenUser);

        assertThat(userDao.findById(givenUser.getId())).isPresent();
        assertEquals(givenUser, userDao.findById(givenUser.getId()).get());
    }

    @Test
    void whenFindByCorrectEmailAndPasswordThenReturnEntity() {
        Optional<User> actual = userDao.findByEmailAndPassword(IVAN.getEmail(), IVAN.getPassword());

        assertThat(actual).isPresent();
        assertEquals(IVAN.getId(), actual.get().getId());
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForFindByEmailAndPassword")
    void whenFindByUncorrectEmailOrPasswordThenReturnEmpty(String email, String password) {
        Optional<User> actual = userDao.findByEmailAndPassword(email, password);

        assertThat(actual).isEmpty();
    }

    static Stream<Arguments> getArgumentsForFindByEmailAndPassword() {
        return Stream.of(
                Arguments.of(IVAN.getEmail(), "dummy"),
                Arguments.of("dummy", IVAN.getPassword()),
                Arguments.of("dummy", "dummy")
        );
    }

    @Test
    void whenDeleteSuccessfullyThenReturnTrue() {
        boolean actual = userDao.delete(IVAN.getId());

        assertTrue(actual);
    }

    @Test
    void whenUpdateThenReturnEntity() {
        User expectedUser = User.builder()
                .id(IVAN.getId())
                .birthday(LocalDate.of(2000, 5, 7))
                .email("test@gmail.com")
                .gender(Gender.FEMALE)
                .name("Test")
                .password("159")
                .role(Role.USER)
                .build();

        userDao.update(expectedUser);

        assertThat(userDao.findById(IVAN.getId())).isPresent();
        User actual = userDao.findById(IVAN.getId()).get();

        assertEquals(expectedUser.getId(), actual.getId());
        assertEquals(expectedUser.getBirthday(), actual.getBirthday());
        assertEquals(expectedUser.getEmail(), actual.getEmail());
        assertSame(expectedUser.getGender(), actual.getGender());
        assertEquals(expectedUser.getName(), actual.getName());
        assertEquals(expectedUser.getPassword(), actual.getPassword());
        assertSame(expectedUser.getRole(), actual.getRole());
    }
}