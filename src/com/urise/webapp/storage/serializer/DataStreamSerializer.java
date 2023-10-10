package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
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
                    case OBJECTIVE, PERSONAL -> {
                        dataOutputStream.writeUTF(((TextSection) entry.getValue()).getContent());
                    }
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
                        writeOrganization(dataOutputStream, organizations);
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
            for(int i = 0; i < sizeSectionType; i++) {
                SectionType sectionType = SectionType.valueOf(dataInputStream.readUTF());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> resume.addSection(sectionType, new TextSection(dataInputStream.readUTF()));
                    case ACHIEVEMENT, QUALIFICATION -> {
                        List<String> list = new ArrayList<>();
                        int size = dataInputStream.readInt();
                        for(i = 0; i < size; i++) {
                            list.add(dataInputStream.readUTF());
                        }
                        resume.addSection(sectionType, new ListSection(list));
                    }
//                    case EXPERIENCE, EDUCATION -> readOrganizations(dis, sectionType, resume);
                }
            }
            return resume;
        }
    }


    private void writeOrganization(DataOutputStream dataOutputStream, List<Organization> organizations) throws IOException {
        for (Organization organization : organizations) {
            dataOutputStream.writeUTF(organization.title());
            dataOutputStream.writeUTF(organization.website());
            List<Organization.Period> periods = organization.periods();
            for (Organization.Period periodList : periods) {
                writeLocalDate(dataOutputStream, periodList.start());
                writeLocalDate(dataOutputStream, periodList.end());
                dataOutputStream.writeUTF(periodList.title());
                dataOutputStream.writeUTF(periodList.description());
            }
        }
    }

    private void writeLocalDate(DataOutputStream dataOutputStream, LocalDate localDate) throws IOException {
        dataOutputStream.writeInt(localDate.getYear());
        dataOutputStream.writeUTF(localDate.getMonth().name());
    }
}
