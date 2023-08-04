package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected Integer getSearchKey(Object uuid) {
        Resume searchRey = new Resume(uuid.toString());
        return Arrays.binarySearch(storage, 0, size, searchRey);
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
