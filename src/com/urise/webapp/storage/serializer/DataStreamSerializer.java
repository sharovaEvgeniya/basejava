package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;
import com.urise.webapp.util.WriteElement;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());
            WriteElement<Map.Entry<ContactType, String>> contactEntry = entry -> {
                dataOutputStream.writeUTF(entry.getKey().name());
                dataOutputStream.writeUTF(entry.getValue());
            };
            writeWithException(resume.getContacts().entrySet(), dataOutputStream, contactEntry);
            WriteElement<Map.Entry<SectionType, Section>> sectionEntry = entry -> {
                dataOutputStream.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case OBJECTIVE, PERSONAL ->
                            dataOutputStream.writeUTF(((TextSection) entry.getValue()).getContent());
                    case ACHIEVEMENT, QUALIFICATION -> {
                        List<String> strings = ((ListSection) entry.getValue()).getStrings();
                        writeWithException(strings, dataOutputStream, dataOutputStream::writeUTF);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> organizations = ((OrganizationSection) entry.getValue()).getOrganization();
                        dataOutputStream.writeInt(organizations.size());
                        for (Organization organization : organizations) {
                            dataOutputStream.writeUTF(organization.title());
                            dataOutputStream.writeUTF(organization.website());
                            List<Organization.Period> periods = organization.periods();
                            dataOutputStream.writeInt(periods.size());
                            for (Organization.Period period : periods) {
                                localDateWrite(dataOutputStream, period.start());
                                localDateWrite(dataOutputStream, period.end());
                                dataOutputStream.writeUTF(period.title());
                                dataOutputStream.writeUTF(period.description());
                            }
                        }
                    }
                }
            };
            writeWithException(resume.getSections().entrySet(), dataOutputStream, sectionEntry);
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
                                LocalDate start = localDateRead(dataInputStream);
                                LocalDate end = localDateRead(dataInputStream);
                                String title = dataInputStream.readUTF();
                                String description = dataInputStream.readUTF();
                                periods.add(new Organization.Period(start, end, title, description));
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

    private void localDateWrite(DataOutputStream dataOutputStream, LocalDate localDate) throws IOException {
        dataOutputStream.writeInt(localDate.getYear());
        dataOutputStream.writeUTF(localDate.getMonth().name());
    }

    private LocalDate localDateRead(DataInputStream dataInputStream) throws IOException {
        return DateUtil.of(dataInputStream.readInt(), Month.valueOf(dataInputStream.readUTF()));
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dataOutputStream,
                                        WriteElement<T> writeElement) throws IOException {
        dataOutputStream.writeInt(collection.size());
        for (T elem : collection) writeElement.write(elem);
    }
}


