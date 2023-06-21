package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;


    protected abstract int findIndex(String uuid);

    public abstract void update(Resume resume);

    public abstract void save(Resume resume);

    public abstract void delete(String uuid);

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
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
