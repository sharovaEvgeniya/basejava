package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public record Organization(String title, String website, List<Period> periods) {
    public Organization {
        Objects.requireNonNull(title, "Title must not be null");
        Objects.requireNonNull(website, "Website must not be null");
        Objects.requireNonNull(periods, "Periods must not be null");
    }
}
