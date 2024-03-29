package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.SectionType.*;

public class ResumeTestData {
    public static Resume createResume(String uuid, String fullName) {
        Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
        contacts.put(NUMBER, "+7(999) 000-00-00");
        contacts.put(SKYPE, "skype");
        contacts.put(EMAIL, "email");
        contacts.put(LINKEDIN, "linkedIn");
        contacts.put(GITHUB, "gitHub");
        contacts.put(STACKOVERFLOW, "stackOverflow");
        contacts.put(HOMEPAGE, "homePage");

        List<String> achievementOrQualification = new ArrayList<>();
        achievementOrQualification.add("achievement or qualification");
        achievementOrQualification.add("lorem lorem lorem");

        LocalDate start1 = LocalDate.of(1990, Month.SEPTEMBER, 10);
        LocalDate end1 = LocalDate.of(2000, Month.JANUARY, 12);
        Organization.Period period1 = new Organization.Period(start1, end1, "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                        "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");

        LocalDate start2 = LocalDate.of(2000, Month.OCTOBER, 12);
        LocalDate end2 = LocalDate.of(2010, Month.NOVEMBER, 30);
        Organization.Period period2 = new Organization.Period(start2, end2, "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.");

        List<Organization.Period> experienceOrEducation = new ArrayList<>();
        experienceOrEducation.add(period1);

        Organization organization = new Organization("Java Online Projects", "http://javaops.ru/", experienceOrEducation);

        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);

        Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);
        sections.put(OBJECTIVE, new TextSection("objective"));
        sections.put(PERSONAL, new TextSection("personal"));
        sections.put(ACHIEVEMENT, new ListSection(achievementOrQualification));
        sections.put(QUALIFICATION, new ListSection(achievementOrQualification));
        sections.put(EXPERIENCE, new OrganizationSection(organizations));
        sections.put(EDUCATION, new OrganizationSection(organizations));

        return new Resume(uuid, fullName, contacts, sections);
    }

    public static void main(String[] args) {
        Resume resume = createResume("uuid", "fullName");

        System.out.println(resume.getFullName() + "\n");
        for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
            System.out.println(entry.getKey().getTitle() + " : " + entry.getValue() + "\n");
        }
        for (Map.Entry<SectionType, Section> entry : resume.getSections().entrySet()) {
            switch (entry.getKey()) {
                case OBJECTIVE, PERSONAL -> {
                    TextSection section = (TextSection) entry.getValue();
                    System.out.println(entry.getKey().getTitle() + " :\n" + section.getContent() + "\n");
                }
                case ACHIEVEMENT, QUALIFICATION -> {
                    List<String> strings = ((ListSection) entry.getValue()).getStrings();
                    System.out.println(entry.getKey().getTitle() + " :\n");
                    for (String string : strings) {
                        System.out.println("-" + string + "\n");
                    }
                }
                case EXPERIENCE, EDUCATION -> {
                    List<Organization> organizations = ((OrganizationSection) entry.getValue()).getOrganizations();
                    System.out.println(entry.getKey().getTitle() + " :\n");
                    for (Organization org : organizations) {
                        System.out.println(org.title() + "   " + org.url());
                        List<Organization.Period> periods = org.periods();
                        for (Organization.Period period : periods) {
                            System.out.println(period.start() + " —— " + period.end() + "  "
                                    + period.title() + "\n" + period.description() + "\n");
                        }
                    }
                }
            }
        }
    }
}
