package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class CompanySection extends Section {
    private List<Organization> organizations;

    public CompanySection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "Organization must not be null");
        this.organizations = organizations;
    }

    public List<Organization> getOrganization() {
        return organizations;
    }
}
