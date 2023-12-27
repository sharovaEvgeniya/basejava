package com.urise.webapp.util;

import com.urise.webapp.model.Resume;
import com.urise.webapp.model.Section;
import com.urise.webapp.model.TextSection;
import org.junit.Assert;
import org.junit.Test;

import static com.urise.webapp.TestData.RESUME_1;

public class JsonParserTest {
    @Test
    public void read() {
        String json = JsonParser.write(RESUME_1, Resume.class);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(RESUME_1, resume);
    }

    @Test
    public void write() {
        Section sectionFirst = new TextSection("objective");
        String json = JsonParser.write(sectionFirst, Section.class);
        System.out.println(json);
        Section sectionSecond = JsonParser.read(json, Section.class);
        Assert.assertEquals(sectionFirst, sectionSecond);
    }
}