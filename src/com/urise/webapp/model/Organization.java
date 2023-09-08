package com.urise.webapp.model;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.urise.webapp.util.DateUtil.NOW;
import static com.urise.webapp.util.DateUtil.of;

public final class Organization {
    private final String title;
    private final String website;
    private final List<Period> periods;

    public Organization(String title, String website, Period... periods) {
        this(title, website, Arrays.asList(periods));
    }

    public Organization(String title, String website, List<Period> periods) {
        Objects.requireNonNull(title, "Title must not be null");
        Objects.requireNonNull(website, "Website must not be null");
        Objects.requireNonNull(periods, "Periods must not be null");
        this.title = title;
        this.website = website;
        this.periods = periods;
    }

    public String title() {
        return title;
    }

    public String website() {
        return website;
    }

    public List<Period> periods() {
        return periods;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Organization) obj;
        return Objects.equals(this.title, that.title) &&
                Objects.equals(this.website, that.website) &&
                Objects.equals(this.periods, that.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, website, periods);
    }

    @Override
    public String toString() {
        return "Organization[" +
                "title=" + title + ", " +
                "website=" + website + ", " +
                "periods=" + periods + ']';
    }

    public static final class Period {
        private final LocalDate start;
        private final LocalDate end;
        private final String title;
        private final String description;

        public Period(int startYear, Month startMonth, String title, String description) {
            this(of(startYear, startMonth), NOW, title, description);
        }

        public Period(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(of(startYear, startMonth), of(endYear, endMonth), title, description);
        }

        public Period(LocalDate start, LocalDate end, String title, String description) {
            Objects.requireNonNull(start, "Start must not be null");
            Objects.requireNonNull(end, "End must not be null");
            Objects.requireNonNull(title, "Title must not be null");
            this.start = start;
            this.end = end;
            this.title = title;
            this.description = description;
        }

        public LocalDate start() {
            return start;
        }

        public LocalDate end() {
            return end;
        }

        public String title() {
            return title;
        }

        public String description() {
            return description;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Period) obj;
            return Objects.equals(this.start, that.start) &&
                    Objects.equals(this.end, that.end) &&
                    Objects.equals(this.title, that.title) &&
                    Objects.equals(this.description, that.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end, title, description);
        }

        @Override
        public String toString() {
            return "Period[" +
                    "start=" + start + ", " +
                    "end=" + end + ", " +
                    "title=" + title + ", " +
                    "description=" + description + ']';
        }

    }
}
