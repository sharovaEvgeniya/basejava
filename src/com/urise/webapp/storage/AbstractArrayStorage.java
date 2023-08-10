package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractArrayStorage extends AbstractStorage {
    private final Logger log = Logger.getLogger(AbstractArrayStorage.class.getName());
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    protected abstract Integer getSearchKey(Object uuid);

    protected abstract void saveResume(int index, Resume resume);

    protected abstract void deleteResume(int index);

    @Override
    public boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    public void doClear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public final void doUpdate(Resume resume, Object searchKey) {
        storage[(int) searchKey] = resume;
        log.info("Update " + resume.getUuid() + " is correct");
    }

    @Override
    public final void doSave(Resume resume, Object searchKey) {
        int index = getSearchKey(resume.getUuid());
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage is full", resume.getUuid());
        } else {
            saveResume(index, resume);
            size++;
            log.info("Add " + resume.getUuid() + " in storage");
        }
    }

    @Override
    public final Resume doGet(Object searchKey) {
        log.info("get " + storage[(int) searchKey].getUuid());
        return storage[(int) searchKey];
    }

    @Override
    public final void doDelete(Object searchKey) {
        deleteResume((Integer) searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public List<Resume> doGetAll() {
        return List.of(Arrays.copyOf(storage, size));
    }

    @Override
    public int doSize() {
        return size;
    }
}
