package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] STORAGE = new Resume[STORAGE_LIMIT];

    protected int size = 0;

    protected abstract Integer getSearchKey(String uuid);

    protected abstract void saveResume(int index, Resume resume);

    protected abstract void deleteResume(int index);

    @Override
    public boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    public void doClear() {
        Arrays.fill(STORAGE, 0, size, null);
        size = 0;
    }

    @Override
    public final void doUpdate(Resume resume, Integer searchKey) {
        STORAGE[searchKey] = resume;
    }

    @Override
    public final void doSave(Resume resume, Integer searchKey) {
        int index = getSearchKey(resume.getUuid());
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage is full", resume.getUuid());
        } else {
            saveResume(index, resume);
            size++;
        }
    }

    @Override
    public final Resume doGet(Integer searchKey) {
        return STORAGE[searchKey];
    }

    @Override
    public final void doDelete(Integer searchKey) {
        deleteResume(searchKey);
        STORAGE[size - 1] = null;
        size--;
    }

    @Override
    public List<Resume> doGetAll() {
        return List.of(Arrays.copyOf(STORAGE, size));
    }

    @Override
    public int doSize() {
        return size;
    }
}
