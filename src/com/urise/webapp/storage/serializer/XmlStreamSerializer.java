package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import com.urise.webapp.util.XmlParser;

public class XmlStreamSerializer {
    private XmlParser xmlParser;
    public XmlStreamSerializer() {
        xmlParser = new XmlParser(Resume.class, Organization.class, ListSection.class,
                OrganizationSection.class, TextSection.class, Organization.Period.class);
    }
}
