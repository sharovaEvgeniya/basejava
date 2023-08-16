package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchRey = new Resume(uuid, "fullName");
        return Arrays.binarySearch(STORAGE, 0, size, searchRey, RESUME_COMPARATOR);
    }

    @Override
    protected void saveResume(int index, Resume resume) {
        int insertionIndex = -getSearchKey(resume.getUuid()) - 1;
        System.arraycopy(STORAGE, insertionIndex, STORAGE, insertionIndex + 1, size - insertionIndex);
        STORAGE[insertionIndex] = resume;
    }

    @Override
    protected void deleteResume(int index) {
        System.arraycopy(STORAGE, index + 1, STORAGE, index, size - index - 1);
    }
}
