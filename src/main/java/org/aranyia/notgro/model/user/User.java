package org.aranyia.notgro.model.user;

import java.time.LocalDate;

public class User {

    private LocalDate registrationDate;

    public User() {
    }

    public User(final LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}
