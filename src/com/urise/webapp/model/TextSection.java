package com.urise.webapp.model;

import java.util.Objects;

public class TextSection extends Section {
    private String content;

    public TextSection(String content) {
        Objects.requireNonNull(content, "Content must not be null");
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
