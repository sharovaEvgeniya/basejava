package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    // Unique identifier
    private String uuid;
    private String fullName;
    private Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume() {
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "Uuid must not be null");
        Objects.requireNonNull(fullName, "FullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public Resume(String uuid, String fullName, Map<ContactType, String> contacts, Map<SectionType, Section> sections) {
        Objects.requireNonNull(uuid, "Uuid must not be null");
        Objects.requireNonNull(fullName, "FullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
        this.contacts = contacts;
        this.sections = sections;
    }

    public Resume(String uuid, String fullName, Map<ContactType, String> contacts) {
        Objects.requireNonNull(uuid, "Uuid must not be null");
        Objects.requireNonNull(fullName, "FullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
        this.contacts = contacts;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public void setContact(ContactType type, String value) {
        contacts.put(type, value);
    }

    public Section getSection(SectionType type) {
        return sections.get(type);
    }

    public String toHtml(SectionType type) {
        String str = "";
        if (type != null) {
            switch (type) {
                case OBJECTIVE, PERSONAL -> {
                    TextSection textSection = (TextSection) sections.get(type);
                    if (textSection == null) {
                        return "";
                    }
                    str = textSection.getContent();
                }
                case ACHIEVEMENT, QUALIFICATION -> {
                    ListSection listSection = (ListSection) sections.get(type);
                    if (listSection == null) {
                        return "";
                    }
                    List<String> stringList = listSection.getStrings();
                    for (String string : stringList) {
                        str += string + ".";
                    }
                }
                case EDUCATION, EXPERIENCE -> {
                    OrganizationSection organizationSection = (OrganizationSection) sections.get(type);
                    if (organizationSection == null) {
                        return "";
                    }
                    List<Organization> orgList = organizationSection.getOrganizations();
                    for (Organization org : orgList) {
                        List<Organization.Period> periods = org.periods();
                        for (Organization.Period period : periods) {
                            str = period.start() + " — " + period.end() + "  " +
                                    period.title() + "\n" + period.description() + "\n";
                        }
                    }
                }
            }
        }
        return str;
    }

    public void setSection(SectionType type, Section section) {
        sections.put(type, section);
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }
}
