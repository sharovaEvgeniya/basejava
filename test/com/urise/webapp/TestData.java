package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.util.UUID;

import static com.urise.webapp.ResumeTestData.createResume;

public class TestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();
    public static final String UUID_NOT_EXIST = "dummy";

    public static final String FULL_NAME_1 = "fullName1";
    public static final String FULL_NAME_2 = "fullName2";
    public static final String FULL_NAME_3 = "fullName3";
    public static final String FULL_NAME_4 = "fullName4";
    public static final String FULL_NAME_NOT_EXIST = "fullName_dummy";

    public static final Resume RESUME_1;
    public static final Resume RESUME_2;
    public static final Resume RESUME_3;
    public static final Resume RESUME_4;
    public static final Resume RESUME_NOT_EXIST;

    static {
        RESUME_1 = createResume(UUID_1, FULL_NAME_1);
        RESUME_2 = createResume(UUID_2, FULL_NAME_2);
        RESUME_3 = createResume(UUID_3, FULL_NAME_3);
        RESUME_4 = createResume(UUID_4, FULL_NAME_4);
        RESUME_NOT_EXIST = createResume(UUID_NOT_EXIST, FULL_NAME_NOT_EXIST);
    }
}
