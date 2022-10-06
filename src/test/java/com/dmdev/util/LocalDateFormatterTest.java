package com.dmdev.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LocalDateFormatterTest {

    @Test
    void formatLocalDateCorrectly() {
        String givenDate = "2015-05-05";

        LocalDate actual = LocalDateFormatter.format(givenDate);

        assertThat(actual).isEqualTo(LocalDate.of(2015, 5, 5));
    }

    @Test
    void throwExeptionWhenLocalDateUncorrect() {
        String givenDate = "05-05-2015";

        assertThrows(DateTimeParseException.class, () -> LocalDateFormatter.format(givenDate));
    }

    @Test
    void whenLocalDateIsValidThenReturnTrue() {
        String givenDate = "2015-05-05";

        assertTrue(LocalDateFormatter.isValid(givenDate));
    }

    @Test
    void whenLocalDateIsInvalidThenReturnFalse() {
        String givenDate = "dummy";

        assertFalse(LocalDateFormatter.isValid(givenDate));
    }

    @Test
    void whenLocalDateIsNullThenReturnFalse() {
        String givenDate = "";
        assertFalse(LocalDateFormatter.isValid(givenDate));
    }
}