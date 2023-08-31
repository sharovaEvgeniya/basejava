package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public record Period(LocalDate start, LocalDate end, String title, String description) {
    public Period {
        Objects.requireNonNull(start, "start must not be null");
        Objects.requireNonNull(title, "title must not be null");
    }
}
