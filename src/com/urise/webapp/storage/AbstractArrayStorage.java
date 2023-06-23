package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    protected abstract int findIndex(String uuid);

    protected abstract void saveResume(int index, Resume resume);

    protected abstract void deleteResume(int index);

    public final void update(Resume resume) {
        int index = findIndex(resume.uuid);
        if (index == -1) {
            System.out.println("Resume with " + resume.uuid + " does not exist");
        } else {
            storage[index] = resume;
            System.out.println("Update " + resume.uuid + " is correct");
        }
    }

    public final void save(Resume resume) {
        int index = findIndex(resume.uuid);
        if (size >= STORAGE_LIMIT) {
            System.out.println("Storage is full");
        } else if (index >= 0) {
            System.out.println("Resume " + resume.uuid + " already exists");
        } else {
            saveResume(index, resume);
            size++;
            System.out.println("Add " + resume.uuid + " in storage");
        }
    }

    public final void delete(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println("Resume with " + uuid + " does not exist");
            return;
        }
        deleteResume(index);
        storage[size - 1] = null;
        size--;
    }

    public int size() {
        return size;
    }

    public final Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println("Resume with " + uuid + " does not exist");
            return null;
        }
        System.out.println("get " + uuid);
        return storage[index];
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }
}
