package com.urise.webapp.model;

public enum ContactType {
    NUMBER("Номер телефона"),
    SKYPE("Skype"),
    EMAIL("E-mail"),
    LINKEDIN("LinkedIn"),
    GITHUB("GitHub"),
    STACKOVERFLOW("StackOverflow"),
    HOMEPAGE("Домашняя страница");
    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "ContactType{" +
                "title='" + title + '\'' +
                '}';
    }
}
