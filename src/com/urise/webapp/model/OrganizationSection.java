package com.urise.webapp.model;

import java.io.Serial;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends Section {
    @Serial
    private static final long serialVersionUID = 1L;
    private final List<Organization> organizations;

    public OrganizationSection(Organization... organization) {
        this(Arrays.asList(organization));
    }

    public OrganizationSection(List<Organization> organizations) {
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
        OrganizationSection that = (OrganizationSection) o;
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
