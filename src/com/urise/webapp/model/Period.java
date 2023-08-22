package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Period {
    private LocalDate start;
    private LocalDate end;
    private String title;
    private String description;

    public Period(LocalDate start, LocalDate end, String title, String description) {
        Objects.requireNonNull(start, "start must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.start = start;
        this.end = end;
        this.title = title;
        this.description = description;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
