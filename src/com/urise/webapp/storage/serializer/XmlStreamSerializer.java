package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import com.urise.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements StreamSerializer {
    private final XmlParser xmlParser;

    public XmlStreamSerializer() {
        xmlParser = new XmlParser(Resume.class, Organization.class, ListSection.class,
                OrganizationSection.class, TextSection.class, Organization.Period.class);
    }

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, writer);
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(reader);
        }
    }
}
