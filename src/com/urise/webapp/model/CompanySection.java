package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class CompanySection extends Section {
    private final List<Organization> organizations;

    public CompanySection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "Organization must not be null");
        this.organizations = organizations;
    }

    public List<Organization> getOrganization() {
        return organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanySection that = (CompanySection) o;
        return Objects.equals(organizations, that.organizations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizations);
    }

    @Override
    public String toString() {
        return "CompanySection{" +
                "organizations=" + organizations +
                '}';
    }
}
