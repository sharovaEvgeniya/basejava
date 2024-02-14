package com.urise.webapp.model;

import java.util.List;

public enum SectionType {
    OBJECTIVE("Позиция"),
    PERSONAL("Личные качества"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATION("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");
    private final String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "SectionType{" +
                "title='" + title + '\'' +
                '}';
    }

    public String toHtml(SectionType type, Section section) {
        String str = "";
        if (type != null) {
            switch (type) {
                case OBJECTIVE, PERSONAL -> {
                    TextSection textSection = (TextSection) section;
                    str = "<div class='section-title'>" + type.title + ": " +
                            "<br>" +
                            "</div>" +
                            "<div class='section-value'>" + textSection.getContent() +
                            "</div>";
                }
                case ACHIEVEMENT, QUALIFICATION -> {
                    List<String> list = ((ListSection) section).getStrings();
                    str = "<div class='section-title'>" + type.title + ": " +
                            "<br>" +
                            "</div>" +
                            "<div class='section-value'>";

                    for (String string : list) {
                        str += "- " + string + "<br>";
                    }
                    str += "</div>";
                }
                case EDUCATION, EXPERIENCE -> {
                    List<Organization> orgList = ((OrganizationSection) section).getOrganizations();
                    for (Organization org : orgList) {
                        str = "<div class='section-title'>" + type.title + ": <br>" +
                                "<a class='link-website' href='" + org.website() + "'>" + org.title() + "</a>" +
                                "<br>" +
                                "</div>";
                        List<Organization.Period> periods = org.periods();
                        for (Organization.Period period : periods) {
                            String startAndEnd = "<div class='section-period-time'>" + period.start() + " — " +
                                    period.end() + "</div>";
                            String titleAndDescription = "<div class='section-period-value'>" +
                                    "<p class='period-title'>" + period.title() + "</p>" + "<br>" + period.description() + "<br>" +
                                    "</div>";
                            str += "<div class='section-value-organization'>"
                                    + startAndEnd + "<br>" + titleAndDescription +
                                    "</div>";
                        }
                    }
                }
            }
        }
        return str;
    }
}
