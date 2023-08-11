package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    protected Integer getSearchKey(Object uuid) {
        for (int i = 0; i < size; i++) {
            if (STORAGE[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void saveResume(int index, Resume resume) {
        STORAGE[size] = resume;
    }

    @Override
    protected void deleteResume(int index) {
        STORAGE[index] = STORAGE[size - 1];
    }
}
