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
            }
        }
        return str;
    }
}
