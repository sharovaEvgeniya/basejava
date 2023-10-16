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

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(outputStream)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            writeWithException(resume.getContacts().entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            writeWithException(resume.getSections().entrySet(), dos, entry -> {
                final SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> dos.writeUTF(((TextSection) entry.getValue()).getContent());
                    case ACHIEVEMENT, QUALIFICATION ->
                            writeWithException(((ListSection) entry.getValue()).getStrings(), dos, dos::writeUTF);
                    case EXPERIENCE, EDUCATION ->
                            writeWithException(((OrganizationSection) entry.getValue()).getOrganization(), dos, organization -> {
                                dos.writeUTF(organization.title());
                                dos.writeUTF(organization.website());
                                writeWithException(organization.periods(), dos, period -> {
                                    localDateWrite(dos, period.start());
                                    localDateWrite(dos, period.end());
                                    dos.writeUTF(period.title());
                                    dos.writeUTF(period.description());
                                });
                            });
                }
            });
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


