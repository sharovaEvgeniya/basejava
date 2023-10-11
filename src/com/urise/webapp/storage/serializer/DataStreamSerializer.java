package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dataOutputStream.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                dataOutputStream.writeUTF(entry.getKey().name());
                dataOutputStream.writeUTF(entry.getValue());
            }
            Map<SectionType, Section> sections = resume.getSections();
            dataOutputStream.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : resume.getSections().entrySet()) {
                dataOutputStream.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case OBJECTIVE, PERSONAL ->
                            dataOutputStream.writeUTF(((TextSection) entry.getValue()).getContent());

                    case ACHIEVEMENT, QUALIFICATION -> {
                        List<String> strings = ((ListSection) entry.getValue()).getStrings();
                        dataOutputStream.writeInt(strings.size());
                        for (String str : strings) {
                            dataOutputStream.writeUTF(str);
                        }
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> organizations = ((OrganizationSection) entry.getValue()).getOrganization();
                        dataOutputStream.writeInt(organizations.size());
                        for (Organization organization : organizations) {
                            dataOutputStream.writeUTF(organization.title());
                            dataOutputStream.writeUTF(organization.website());
                            List<Organization.Period> periods = organization.periods();
                            dataOutputStream.writeInt(periods.size());
                            for (Organization.Period periodList : periods) {
                                dataOutputStream.writeInt(periodList.start().getYear());
                                dataOutputStream.writeUTF(periodList.start().getMonth().name());
                                dataOutputStream.writeInt(periodList.end().getYear());
                                dataOutputStream.writeUTF(periodList.end().getMonth().name());
                                dataOutputStream.writeUTF(periodList.title());
                                dataOutputStream.writeUTF(periodList.description());
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            String uuid = dataInputStream.readUTF();
            String fullName = dataInputStream.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int sizeContactType = dataInputStream.readInt();
            for (int i = 0; i < sizeContactType; i++) {
                resume.addContact(ContactType.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF());
            }
            int sizeSectionType = dataInputStream.readInt();
            for (int i = 0; i < sizeSectionType; i++) {
                SectionType sectionType = SectionType.valueOf(dataInputStream.readUTF());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE ->
                            resume.addSection(sectionType, new TextSection(dataInputStream.readUTF()));
                    case ACHIEVEMENT, QUALIFICATION -> {
                        List<String> list = new ArrayList<>();
                        int size = dataInputStream.readInt();
                        for (int j = 0; j < size; j++) {
                            list.add(dataInputStream.readUTF());
                        }
                        resume.addSection(sectionType, new ListSection(list));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> organizationList = new ArrayList<>();
                        int sizeOrg = dataInputStream.readInt();
                        for (int j = 0; j < sizeOrg; j++) {
                            Organization organization = new Organization();
                            organization.setTitle(dataInputStream.readUTF());
                            organization.setWebsite(dataInputStream.readUTF());
                            List<Organization.Period> periods = new ArrayList<>();
                            int sizePer = dataInputStream.readInt();
                            for (int k = 0; k < sizePer; k++) {
                                int startYear = dataInputStream.readInt();
                                String startMonth = dataInputStream.readUTF();
                                int endYear = dataInputStream.readInt();
                                String endMonth = dataInputStream.readUTF();
                                String title = dataInputStream.readUTF();
                                String description = dataInputStream.readUTF();
                                periods.add(new Organization.Period(startYear, Month.valueOf(startMonth), endYear,
                                        Month.valueOf(endMonth), title, description));
                                organization.setPeriods(periods);
                            }
                            organizationList.add(organization);
                        }
                        resume.addSection(sectionType, new OrganizationSection(organizationList));
                    }
                }
            }
            return resume;
        }
    }
}
