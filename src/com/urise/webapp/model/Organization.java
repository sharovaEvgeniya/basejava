package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class Organization {
    private String title;
    private String website;
    private List<Period> periods;


    public Organization(String title, String website, List<Period> periods) {
        Objects.requireNonNull(title, "Title must not be null");
        Objects.requireNonNull(website, "Website must not be null");
        Objects.requireNonNull(periods, "Periods must not be null");
        this.title = title;
        this.website = website;
        this.periods = periods;
    }

    public String getTitle() {
        return title;
    }

    public String getWebsite() {
        return website;
    }

    public List<Period> getPeriods() {
        return periods;
    }
}
