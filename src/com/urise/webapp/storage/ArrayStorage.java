package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    protected final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        int index = findIndex(resume.uuid);
        if (size == STORAGE_LIMIT) {
            System.out.println("Storage is full");
        } else if (index != -1) {
            System.out.println("Resume " + resume.uuid + " already exists");
        } else {
            storage[size] = resume;
            size++;
            System.out.println("Add " + resume.uuid + " in storage");
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println("Resume with " + uuid + " does not exist");
        } else {
            System.out.println("get " + uuid);
            return storage[index];
        }
        return null;
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println("Resume with " + uuid + " does not exist");
            return;
        }
        storage[index] = storage[size - 1];
        size--;
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public void update(Resume resume) {
        int index = findIndex(resume.uuid);
        if (index == -1) {
            System.out.println("Resume with " + resume.uuid + " does not exist");
        } else {
            storage[index] = resume;
            System.out.println("Update " + resume.uuid + " is correct");
        }
    }
}
