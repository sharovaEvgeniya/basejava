package com.urise.webapp;

import com.urise.webapp.model.*;

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
//
//        LocalDate start1 = LocalDate.of(9999, Month.SEPTEMBER, 1);
//        LocalDate end1 = LocalDate.of(9999, Month.JANUARY, 1);
//        Organization.Period period1 = new Organization.Period(start1, end1, "title", "description");
//
//        LocalDate start2 = LocalDate.of(8888, Month.OCTOBER, 1);
//        LocalDate end2 = LocalDate.of(8888, Month.NOVEMBER, 1);
//        Organization.Period period2 = new Organization.Period(start2, end2, "title", "description");
//
//        List<Organization.Period> experienceOrEducation = new ArrayList<>();
//        experienceOrEducation.add(period1);
//        experienceOrEducation.add(period2);
//
//        Organization organization = new Organization("title", "website", experienceOrEducation);
//
//        List<Organization> organizations = new ArrayList<>();
//        organizations.add(organization);
//
        Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);
        sections.put(OBJECTIVE, new TextSection("objective"));
        sections.put(PERSONAL, new TextSection("personal"));
        sections.put(ACHIEVEMENT, new ListSection(achievementOrQualification));
        sections.put(QUALIFICATION, new ListSection(achievementOrQualification));
//        sections.put(EXPERIENCE, new OrganizationSection(organizations));
//        sections.put(EDUCATION, new OrganizationSection(organizations));
//
//        return new Resume(uuid, fullName, contacts, sections);
        return new Resume(uuid, fullName, contacts,sections);
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
                    List<Organization> organizations = ((OrganizationSection) entry.getValue()).getOrganization();
                    System.out.println(entry.getKey().getTitle() + " :\n");
                    for (Organization org : organizations) {
                        System.out.println(org.title() + "   " + org.website());
                        List<Organization.Period> periods = org.periods();
                        for (Organization.Period periodList : periods) {
                            System.out.println(periodList.start() + " —— " + periodList.end() + "  "
                                    + periodList.title() + "\n" + periodList.description() + "\n");
                        }
                    }
                }
            }
        }
    }
}
