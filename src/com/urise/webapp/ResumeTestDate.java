package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.SectionType.*;

public class ResumeTestDate {
    public static void main(String[] args) {
        TextSection objectiveText = new TextSection("Ведущий стажировок и корпоративного обучения" +
                " по Java Web и Enterprise технологиям");
        TextSection personalText = new TextSection("Аналитический склад ума, сильная логика, креативность, " +
                "инициативность. Пурист кода и архитектуры.");

        List<String> achievement = new ArrayList<>();
        achievement.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: " +
                "приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов" +
                " на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot" +
                " + Vaadin проект для комплексных DIY смет");
        achievement.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                "Более 3500 выпускников.");
        achievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievement.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция" +
                " с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
                "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей," +
                " интеграция CIFS/SMB java сервера.");
        achievement.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring," +
                " Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievement.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов" +
                " (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии" +
                " через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга" +
                " системы по JMX (Jython/ Django).");
        achievement.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat," +
                " Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        List<String> qualification = new ArrayList<>();
        qualification.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualification.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualification.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, " +
                "MySQL, SQLite, MS SQL, HSQLDB");
        qualification.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualification.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualification.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring " +
                "(MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT)," +
                " Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualification.add("Python: Django.");
        qualification.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualification.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualification.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX," +
                " DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, " +
                "OAuth1, OAuth2, JWT.");
        qualification.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        qualification.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios," +
                " iReport, OpenCmis, Bonita, pgBouncer");
        qualification.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, " +
                "архитектурных шаблонов, UML, функционального программирования");
        qualification.add("Родной русский, английский \"upper intermediate\"");

        LocalDate startAlcatel = LocalDate.of(1997, Month.SEPTEMBER, 25);
        LocalDate endAlcatel = LocalDate.of(2005, Month.JANUARY, 10);
        String titleAlcatel = "Инженер по аппаратному и программному тестированию";
        String descriptionAlcatel = "Тестирование, отладка, внедрение ПО цифровой " +
                "телефонной станции Alcatel 1000 S12 (CHILL, ASM).";

        LocalDate startAlcatel1 = LocalDate.of(9888, Month.SEPTEMBER, 25);
        LocalDate endAlcatel1 = LocalDate.of(9999, Month.JANUARY, 10);
        String titleAlcatel1 = "Инженер по аппаратному и программному тестированию";
        Period periodAlcatel1 = new Period(startAlcatel1, endAlcatel1, titleAlcatel1, null);
        Period periodAlcatel = new Period(startAlcatel, endAlcatel, titleAlcatel, descriptionAlcatel);

        List<Period> periodsExperience = new ArrayList<>();
        periodsExperience.add(periodAlcatel);
        periodsExperience.add(periodAlcatel1);

        LocalDate startLuxoft = LocalDate.of(2011, Month.MARCH, 19);
        LocalDate endLuxoft = LocalDate.of(2011, Month.APRIL, 13);
        String titleLuxoft = "Luxoft";
        String descriptionLuxoft = "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'";
        Period periodLuxoft = new Period(startLuxoft, endLuxoft, titleLuxoft, descriptionLuxoft);
        List<Period> periodsEducation = new ArrayList<>();
        periodsEducation.add(periodLuxoft);

        Organization organizationAlcatel = new Organization("Alcatel",
                "http://www.alcatel.ru/", periodsExperience);
        List<Organization> listOrganizationExperience = new ArrayList<>();
        listOrganizationExperience.add(organizationAlcatel);


        Organization organizationLuxoft = new Organization("Luxoft", "prmotion.me", periodsEducation);
        List<Organization> listOrganizationEducation = new ArrayList<>();
        listOrganizationEducation.add(organizationLuxoft);


        ListSection listSectionAchievement = new ListSection(achievement);
        ListSection listSectionQualification = new ListSection(qualification);
        CompanySection companySectionExperience = new CompanySection(listOrganizationExperience);
        CompanySection companySectionEducation = new CompanySection(listOrganizationEducation);
        Map<SectionType, Section> resumeSections = new LinkedHashMap<>();
        resumeSections.put(OBJECTIVE, objectiveText);
        resumeSections.put(PERSONAL, personalText);
        resumeSections.put(ACHIEVEMENT, listSectionAchievement);
        resumeSections.put(QUALIFICATION, listSectionQualification);
        resumeSections.put(EXPERIENCE, companySectionExperience);
        resumeSections.put(EDUCATION, companySectionEducation);

        Map<ContactType, String> resumeContacts = new LinkedHashMap<>();
        resumeContacts.put(NUMBER, "+7(921) 855-0482");
        resumeContacts.put(SKYPE, "grigory.kislin");
        resumeContacts.put(EMAIL, "gkislin@yandex.ru");
        resumeContacts.put(LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resumeContacts.put(GITHUB, "https://github.com/gkislin");
        resumeContacts.put(STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");
        resumeContacts.put(HOMEPAGE, "http://gkislin.ru/");

        Resume resume = new Resume("11111111-1111-1111-1111-111111111111", "Grigorii Kislin",
                resumeContacts, resumeSections);

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
                    List<Organization> organizations = ((CompanySection) entry.getValue()).getOrganization();
                    System.out.println(entry.getKey().getTitle() + " :\n");
                    for (Organization org : organizations) {
                        System.out.println(org.title() + "   " + org.website());
                        List<Period>[] periods = org.periods();
                        System.out.println(periods.length);
                        for (List<Period> periodList : periods) {
                            for (Period period : periodList) {
                                System.out.println(period.start() + " —— " + period.end() + "  "
                                        + period.title() + "\n" + period.description() + "\n");
                            }
                        }
                    }
                }
            }
        }
    }
}
