package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.logging.Logger;

public abstract class AbstractArrayStorage extends AbstractStorage {
    private final Logger log = Logger.getLogger(AbstractArrayStorage.class.getName());
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;


    protected abstract int getIndex(String uuid);

    protected abstract void saveResume(int index, Resume resume);

    protected abstract void deleteResume(int index);

    public void clearStorage() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public final void updateStorage(Resume resume) {
        int index = getIndex(resume.getUuid());
        storage[index] = resume;
        log.info("Update " + resume.getUuid() + " is correct");
    }

    public final void saveStorage(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage is full", resume.getUuid());
        } else {
            saveResume(index, resume);
            size++;
            log.info("Add " + resume.getUuid() + " in storage");
        }
    }

    public final Resume getStorage(String uuid) {
        int index = getIndex(uuid);
        log.info("get " + uuid);
        return storage[index];
    }

    public final void deleteStorage(String uuid) {
        int index = getIndex(uuid);
        deleteResume(index);
        storage[size - 1] = null;
        size--;
    }

    public Resume[] getAllStorage() {
        return Arrays.copyOf(storage, size);
    }

    public int sizeStorage() {
        return size;
    }
}
