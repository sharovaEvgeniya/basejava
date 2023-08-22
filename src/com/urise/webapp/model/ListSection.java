package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    private List<String> strings;

    public ListSection(List<String> strings) {
        Objects.requireNonNull(strings, "String must not be null");
        this.strings = strings;
    }

    public List<String> getStrings() {
        return strings;
    }
}
