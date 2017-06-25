package org.aranyia.notgro.model.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDate;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class UserTest {

    @Test
    public void testConstructorWithLocalDate() {
        //given
        final LocalDate registrationDate = LocalDate.now();
        final User user = new User(registrationDate);

        //when
        final LocalDate actualRegistrationDate = user.getRegistrationDate();

        //then
        assertThat(registrationDate, is(actualRegistrationDate));
    }

    @Test
    public void testSetRegistrationDate() {
        //given
        final LocalDate registrationDate = LocalDate.now();
        final User user = new User();

        //when
        user.setRegistrationDate(registrationDate);
        final LocalDate actualRegistrationDate = user.getRegistrationDate();

        //then
        assertThat(registrationDate, is(actualRegistrationDate));
    }
}
