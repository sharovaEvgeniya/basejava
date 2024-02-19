package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;
import com.urise.webapp.util.ReadCollection;
import com.urise.webapp.util.ReadElement;
import com.urise.webapp.util.WriteCollection;

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
                            writeWithException(((OrganizationSection) entry.getValue()).getOrganizations(), dos, organization -> {
                                dos.writeUTF(organization.title());
                                dos.writeUTF(organization.url());
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

    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dis = new DataInputStream(inputStream)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());

            readElementWithException(dis, () -> resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            readElementWithException(dis, () -> {
                final SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> resume.setSection(sectionType, new TextSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATION ->
                            resume.setSection(sectionType, new ListSection(readCollectionWithException(dis, dis::readUTF)));
                    case EXPERIENCE, EDUCATION -> resume.setSection(sectionType,
                            new OrganizationSection(
                                    readCollectionWithException(dis, () ->
                                            new Organization(dis.readUTF(), dis.readUTF(), readCollectionWithException(dis, () ->
                                                    new Organization.Period(localDateRead(dis), localDateRead(dis), dis.readUTF(), dis.readUTF()))))));
                }
            });
            return resume;
        }
    }

    private void localDateWrite(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeUTF(localDate.getMonth().name());
    }

    private LocalDate localDateRead(DataInputStream dis) throws IOException {
        return DateUtil.of(dis.readInt(), Month.valueOf(dis.readUTF()));
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, WriteCollection<T> writeCollection) throws IOException {
        dos.writeInt(collection.size());
        for (T elem : collection) writeCollection.write(elem);
    }

    private <T> List<T> readCollectionWithException(DataInputStream dis, ReadCollection<T> readCollection) throws IOException {
        List<T> list = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            list.add(readCollection.read());
        }
        return list;
    }

    private void readElementWithException(DataInputStream dis, ReadElement readElement) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            readElement.read();
        }
    }
}


