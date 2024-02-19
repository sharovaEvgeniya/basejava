package com.urise.webapp.model;

import com.urise.webapp.util.XmlLocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.urise.webapp.util.DateUtil.NOW;
import static com.urise.webapp.util.DateUtil.of;

@XmlAccessorType(XmlAccessType.FIELD)
public final class Organization implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public static final Organization EMPTY = new Organization("", "", Period.EMPTY);
    private String title;
    private String url;
    private List<Period> periods;

    public Organization(String url, List<Period> periods) {
        this.url = url;
        this.periods = periods;
    }

    public Organization(String title, String url, Period... periods) {
        this(title, url, Arrays.asList(periods));
    }

    public Organization(String title, String url, List<Period> periods) {
        Objects.requireNonNull(periods, "Periods must not be null");
        this.title = title != null ? title : "";
        this.url = url != null ? url : "";
        this.periods = periods;
    }

    public String title() {
        return title;
    }

    public String url() {
        return url;
    }

    public List<Period> periods() {
        return periods;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(title, that.title) && Objects.equals(url, that.url) && Objects.equals(periods, that.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, url, periods);
    }

    @Override
    public String toString() {
        return "Organization[" +
                "title=" + title + ", " +
                "website=" + url + ", " +
                "periods=" + periods + ']';
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static final class Period implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;
        public static final Period EMPTY = new Period();
        @XmlJavaTypeAdapter(XmlLocalDateAdapter.class)
        private LocalDate start;
        @XmlJavaTypeAdapter(XmlLocalDateAdapter.class)
        private LocalDate end;
        private String title;
        private String description;

        public Period() {
        }

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
            this.description = description != null ? description : "";
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

        public LocalDate getStart() {
            return start;
        }

        public void setStart(LocalDate start) {
            this.start = start;
        }

        public LocalDate getEnd() {
            return end;
        }

        public void setEnd(LocalDate end) {
            this.end = end;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Period period = (Period) o;
            return Objects.equals(start, period.start) &&
                    Objects.equals(end, period.end) &&
                    Objects.equals(title, period.title) &&
                    Objects.equals(description, period.description);
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
