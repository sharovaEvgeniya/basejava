package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected Integer getSearchKey(Object uuid) {
        Resume searchRey = new Resume((String) uuid, "fullName");
        return Arrays.binarySearch(storage, 0, size, searchRey, RESUME_COMPARATOR);
    }

    @Override
    protected void saveResume(int index, Resume resume) {
        int insertionIndex = -getSearchKey(resume.getUuid()) - 1;
        System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, size - insertionIndex);
        storage[insertionIndex] = resume;
    }

    @Override
    protected void deleteResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }
}
